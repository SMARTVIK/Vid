package com.hunter.videostatus.ui.fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.hunter.videostatus.BuildConfig;
import com.hunter.videostatus.R;
import com.hunter.videostatus.adapter.LatestVideoAdapter;
import com.hunter.videostatus.listeners.EndlessRecyclerViewScrollListener;
import com.hunter.videostatus.listeners.OnPopUpShareListener;
import com.hunter.videostatus.model.Status;
import com.hunter.videostatus.ui.activity.ListAllApps;
import com.hunter.videostatus.ui.activity.SpacesItemDecoration;
import com.hunter.videostatus.ui.activity.TabActivity;
import com.hunter.videostatus.ui.activity.VideoDetailScreen;
import com.hunter.videostatus.util.Utility;
import com.hunter.videostatus.vidstatus.Api;
import com.hunter.videostatus.vidstatus.Constant;
import com.hunter.videostatus.vidstatus.StatusApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class LatestContentFragment extends Fragment implements OnPopUpShareListener {

    private static final String TAG = LatestContentFragment.class.getSimpleName();
    private String catString;
    private RecyclerView list;
    private View mRootView;
    private InterstitialAd mInterstitialAd;
    private Status.DataBean currentDataBean;
    private int sharingCurrent;

    public LatestContentFragment() {
        // Required empty public constructor
    }

    private LatestVideoAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ArrayList<Status.DataBean> dataBeans = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_content, container, false);
        initViews(mRootView);
        getLatestVideoStatus(1,true);
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(BuildConfig.DEBUG?Constant.TEST_INTERSTITIAL:Constant.LATEST_CONTENT);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        return mRootView;
    }

    private void getLatestVideoStatus(int page,boolean mainLoader) {
        if (mainLoader) {
            Utility.showLoader(mRootView);
        }else{
            adapter.setShowProgress(true);
        }
        Api api = new StatusApi().getStatusApi();
        final Call<Status> req = getCall(api, page);
        req.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Log.d("onResponse", response.isSuccessful() + "");
                if (dataBeans.isEmpty()) {
                    dataBeans = response.body().getData();
                } else {
                    dataBeans.addAll(response.body().getData());
                }
                Utility.hideLoader(mRootView);
                adapter.setType(catString);
                adapter.setData(dataBeans);
                adapter.setShowProgress(false);
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.d("onFailure", t.getMessage() + "");
                Utility.hideLoader(mRootView);
            }
        });
    }

    private Call<Status> getCall(Api api, int page) {

        Call<Status> req = null;

        switch (catString) {

            case "Video":
                req = api.getLatestVideoStatus(page);
                break;

            case "Text":
                req = api.getLatestTextStatus(page);
                break;

            case "Image":
                req = api.getLatestImageStatus(page);
                break;
        }

        return req;
    }

    private void initViews(View v) {
        list = v.findViewById(R.id.rv_latest_video_status);

        list.setHasFixedSize(true);
        list.setItemViewCacheSize(20);
        list.setDrawingCacheEnabled(true);
        list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        if (!catString.equals("Text")) {
            list.addItemDecoration(new SpacesItemDecoration(10));
        } else {
            list.addItemDecoration(new SpacesItemDecoration(0));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(linearLayoutManager);
        adapter = new LatestVideoAdapter(getContext());
        adapter.setOnShareClickListener(this);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int page, int totalItemsCount, RecyclerView view) {
                Log.d(TAG, "page number " + page);
               list.post(new Runnable() {
                   @Override
                   public void run() {
                       getLatestVideoStatus(page,false);
                   }
               });
            }
        };
        list.setAdapter(adapter);
        list.addOnScrollListener(scrollListener);
    }

    public void addTag(String catString) {
        this.catString = catString;
        if (list != null) {
            if (!catString.equals("Text")) {
                list.addItemDecoration(new SpacesItemDecoration(10));
            } else {
                list.addItemDecoration(new SpacesItemDecoration(0));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            switch (sharingCurrent) {
                case 1:
                    onWhatsUpClick(currentDataBean);
                    break;
                case 2:
                    onInstaClick(currentDataBean);
                    break;
                case 3:
                    onMessangerClick(currentDataBean);
                    break;
                case 4:
                    onFacebookClick(currentDataBean);
                    break;
            }
        }
    }

    @Override
    public void onWhatsUpClick(Status.DataBean dataBean) {
        currentDataBean = dataBean;
        sharingCurrent = 1;
        if (!Utility.checkPermissionForStorage(getActivity())) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return;
        }
        new Download_GIF(dataBean.getVideourl(), 1).execute();
    }

    @Override
    public void onInstaClick(Status.DataBean dataBean) {
        currentDataBean = dataBean;
        sharingCurrent = 2;
        if (!Utility.checkPermissionForStorage(getActivity())) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return;
        }
        new Download_GIF(dataBean.getVideourl(), 2).execute();
    }

    @Override
    public void onMessangerClick(Status.DataBean dataBean) {
        currentDataBean = dataBean;
        sharingCurrent = 3;
        if (!Utility.checkPermissionForStorage(getActivity())) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return;
        }
        new Download_GIF(dataBean.getVideourl(), 3).execute();
    }

    @Override
    public void onFacebookClick(Status.DataBean dataBean) {
        currentDataBean = dataBean;
        sharingCurrent = 4;
        if (!Utility.checkPermissionForStorage(getActivity())) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return;
        }
        new Download_GIF(dataBean.getVideourl(), 4).execute();
    }

    @Override
    public void onItemClick(Status.DataBean bean, ArrayList<Status.DataBean> results) {
         if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        getContext().startActivity(new Intent(getContext(), VideoDetailScreen.class).putExtra("item", bean)
                .putParcelableArrayListExtra("remaining_list", results));
    }

    public class Download_GIF extends AsyncTask<String, int[], String> {

        private final int current;
        private String url_image=null;
        private ProgressDialog progressDialog;

        public Download_GIF(String url, int current) {
            this.url_image = url;
            this.current = current;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Sharing File...");
            progressDialog.setCancelable(false);
            progressDialog.setProgress(0);
            progressDialog.setMax(0);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(final int[]... values) {
            super.onProgressUpdate(values);
            ((TabActivity)getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int[] max = values[0];
                    progressDialog.setMax(max[0]);
                    progressDialog.setProgress(max[1]);
                }
            });
        }

        @Override
        protected String doInBackground(String... params) {

            String filepath = null;
            try {
                //set the download URL, a url that points to a file on the internet
                //this is the file to be downloaded
                URL url = new URL(url_image);
                //create the new connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //set up some things on the connection
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                //and connect!
                urlConnection.connect();
                //set the path where we want to save the file
                //in this case, going to save it on the root directory of the
                //sd card.
                File SDCardRoot = Environment.getExternalStorageDirectory();
                //create a new file, specifying the path, and the filename
                //which we want to save the file as.
                String filename = "video." + url_image.substring(url_image.lastIndexOf("."));   // you can download to any type of file ex:.jpeg (image) ,.txt(text file),.mp3 (audio file)
                Log.i("Local filename:", "" + filename);
                File file;
                file = new File(SDCardRoot, filename);
                if (file.createNewFile()) {
                    file.createNewFile();
                }
                //this will be used to write the downloaded data into the file we created
                FileOutputStream fileOutput = new FileOutputStream(file);
                //this will be used in reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();
                //this is the total size of the file
                int totalSize = urlConnection.getContentLength();
                //variable to store total downloaded bytes
                int downloadedSize = 0;
                //create a buffer...
                byte[] buffer = new byte[1024];
                int bufferLength = 0; //used to store a temporary size of the buffer
                //now, read through the input buffer and write the contents to the file
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    //add the data in the buffer to the file in the file output stream (the file on the sd card
                    fileOutput.write(buffer, 0, bufferLength);
                    //add up the size so we know how much is downloaded
                    downloadedSize += bufferLength;
                    int[] values = {totalSize, downloadedSize};
                    publishProgress(values);
                    //this is where you would do something to report the prgress, like this maybe
                    Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
                }
                //close the output stream when done
                fileOutput.close();
//                if (downloadedSize == totalSize)
                filepath = file.getPath();
                //catch some possible errors...
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                filepath = null;
                e.printStackTrace();
            }
            Log.i("filepath:", " " + filepath);
            return filepath;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (s == null) {
                return;
            }
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            whatsappIntent.setType("*/*");
            Uri photoURI = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".fileprovider", new File(s));
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            whatsappIntent.setAction(ACTION_SEND);
            if(current == 4){
                startActivity(Intent.createChooser(whatsappIntent, "sharing video file"));
            }else{
                String packageName = Utility.getPackageName(current);
                whatsappIntent.setPackage(packageName);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setData(Uri.parse("market://details?id=" + packageName));
                        startActivity(intent);
                    } catch (Exception e) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            }
        }
    }
}