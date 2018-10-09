package com.hunter.videostatus.ui.activity;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.InterstitialAd;
import com.hunter.videostatus.R;
import com.hunter.videostatus.adapter.GifAdapter;
import com.hunter.videostatus.adapter.GifListAdapter;
import com.hunter.videostatus.gifandvideos.ApiClient;
import com.hunter.videostatus.gifandvideos.GifCategories;
import com.hunter.videostatus.gifandvideos.MainPojoGIF;
import com.hunter.videostatus.listeners.OnShareItemsListener;
import com.hunter.videostatus.ui.fragments.CategoryFragment;
import com.hunter.videostatus.ui.fragments.TabFragment;
import com.hunter.videostatus.util.Utility;
import com.hunter.videostatus.vidstatus.Api;
import com.hunter.videostatus.vidstatus.StatusApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class MainActivity extends AppCompatActivity implements OnShareItemsListener {

    Toolbar toolbar;
    private GifListAdapter gifAdapter;
    private boolean homeEnabled;
    private List<MainPojoGIF.DataBean> dataBeans = new ArrayList<>();
    private int current;
    private MainPojoGIF.DataBean data;
    // status bar color #00796B

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.header_color));
        }
        //        MobileAds.initialize(this, "ca-app-pub-3748283843614648~4916776186");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getTitleText());
        getSupportActionBar().setHomeButtonEnabled(true);
        setUpList();
        homeEnabled = true;
        getGifByCat(getIntent().getStringExtra("cat_id"));
    }

    private String getTitleText() {
        return getIntent().getStringExtra("cat_name");
    }

    private void setUpList() {
        RecyclerView gifList = findViewById(R.id.rv_latest_video_status);
        gifList.addItemDecoration(new SpacesItemDecoration(10));
        LinearLayoutManager staggeredGridLayoutManager = new LinearLayoutManager(this);
        staggeredGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gifList.setLayoutManager(staggeredGridLayoutManager);
        gifAdapter = new GifListAdapter(this);
        gifAdapter.setOnShareItemsListener(this);
        gifList.setAdapter(gifAdapter);
    }

    private void getGifByCat(String cat) {
        Utility.showLoader(findViewById(R.id.main_content));
        Api api = new StatusApi().getGifApi();
        final Call<MainPojoGIF> req = api.getGifById(cat,"1",20);
        req.enqueue(new Callback<MainPojoGIF>() {
            @Override
            public void onResponse(Call<MainPojoGIF> call, Response<MainPojoGIF> response) {
                Log.d("onResponse", response.isSuccessful() + "");
                dataBeans = response.body().getData();
                Collections.shuffle(dataBeans);
                gifAdapter.setData(dataBeans);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utility.hideLoader(findViewById(R.id.main_content));
                    }
                }, 500);
            }

            @Override
            public void onFailure(Call<MainPojoGIF> call, Throwable t) {
                Log.d("onFailure", t.getMessage() + "");
                Utility.hideLoader(findViewById(R.id.main_content));
            }
        });
    }

    @Override
    public void onWhatsUpClick(MainPojoGIF.DataBean dataBean) {
        this.data = dataBean;
        if (!checkPermissionForStorage(1)) {
            requestPermission();
            return;
        }
        new Download_GIF(ApiClient.BASE_URL_GIF + dataBean.getUrl(),1).execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            switch (current) {

                case 1:
                    onWhatsUpClick(data);
                    break;
                case 2:
                    onMessangerClick(data);
                    break;
                case 3:
                    onFacebookClick(data);
                    break;
            }
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private boolean checkPermissionForStorage(int i) {
        current = i;
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onInstaClick(MainPojoGIF.DataBean dataBean) {
        this.data = dataBean;
        if (!checkPermissionForStorage(2)) {
            requestPermission();
            return;
        }
        new Download_GIF(ApiClient.BASE_URL_GIF + dataBean.getUrl(), 2).execute();
    }

    @Override
    public void onMessangerClick(MainPojoGIF.DataBean dataBean) {
        this.data = dataBean;
        if (!checkPermissionForStorage(3)) {
            requestPermission();
            return;
        }
        new Download_GIF(ApiClient.BASE_URL_GIF + dataBean.getUrl(), 3).execute();
    }

    @Override
    public void onFacebookClick(MainPojoGIF.DataBean dataBean) {
        this.data = dataBean;
        if (!checkPermissionForStorage(4)) {
            requestPermission();
            return;
        }
        new Download_GIF(ApiClient.BASE_URL_GIF + dataBean.getUrl(), 4).execute();
    }
    public class Download_GIF extends AsyncTask<String, Void, String> {

        private final int current;
        String url_image=null;

        public Download_GIF(String url, int current) {
            this.url_image = url;
            this.current = current;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                String filename = "downloadedFile.gif";   // you can download to any type of file ex:.jpeg (image) ,.txt(text file),.mp3 (audio file)
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
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            whatsappIntent.setType("image/*");
            Uri photoURI = FileProvider.getUriForFile(MainActivity.this, getApplicationContext().getPackageName() + ".fileprovider", new File(s));
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (current == 4) {
                startActivity(Intent.createChooser(whatsappIntent, "sharing Gif file"));
            } else {
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