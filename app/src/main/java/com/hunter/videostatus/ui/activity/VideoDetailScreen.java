package com.hunter.videostatus.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.videostatus.R;
import com.hunter.videostatus.adapter.LatestVideoAdapter;
import com.hunter.videostatus.adapter.VideoListAdapter;
import com.hunter.videostatus.listeners.EndlessRecyclerViewScrollListener;
import com.hunter.videostatus.listeners.OnVideoClickListener;
import com.hunter.videostatus.model.Status;
import com.hunter.videostatus.util.Utility;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class VideoDetailScreen extends AppCompatActivity implements OnVideoClickListener {
    private static final String TAG = VideoDetailScreen.class.getSimpleName();
    View mBottomLayout;
    View mVideoLayout;
    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;
    private boolean isFullscreen;
    private int cachedHeight;
    private ArrayList<Status.DataBean> results = new ArrayList<>();
    private RecyclerView list;
    private VideoListAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private Status.DataBean currentVideo;

    @Override
    protected void onResume() {
        super.onResume();
        if (currentVideo != null) {
            mVideoView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        mBottomLayout = findViewById(R.id.bottom_layout);
        mVideoLayout = findViewById(R.id.video_layout);
        results = getIntent().getParcelableArrayListExtra("remaining_list");
        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mVideoView.setMediaController(mMediaController);
        setUpListOfVideos();
        mVideoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {
                VideoDetailScreen.this.isFullscreen = isFullscreen;
                if (isFullscreen) {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    mVideoLayout.setLayoutParams(layoutParams);
                    //GONE the unconcerned views to leave room for video and controller
                    mBottomLayout.setVisibility(View.GONE);
                } else {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = VideoDetailScreen.this.cachedHeight;
                    mVideoLayout.setLayoutParams(layoutParams);
                    mBottomLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) { // Video pause
                Log.d(TAG, "onPause UniversalVideoView callback");
            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) { // Video start/resume to play
                Log.d(TAG, "onStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {// steam start loading
                Log.d(TAG, "onBufferingStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {// steam end loading
                Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
            }

        });
        currentVideo = getIntent().getParcelableExtra("item");
        mVideoView.setVideoPath(currentVideo.getVideourl());
        mVideoView.start();
        initShareLayout();
    }

    private void initShareLayout() {
        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPermissionForStorage()) {
                    requestPermission();
                    return;
                }
                new Download_GIF(currentVideo.getVideourl()).execute();
            }
        });
    }

    private void setUpListOfVideos() {
        list = findViewById(R.id.bottom_layout);
        list.addItemDecoration(new SpacesItemDecoration(10));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(linearLayoutManager);
        adapter = new VideoListAdapter(this);
        adapter.setOnItemClickListener(this);
        list.setAdapter(adapter);
        Collections.shuffle(results);
        adapter.setData(results);
    }

    @Override
    public void onVideoClick(Status.DataBean bean) {
        String videoUrl = bean.getVideourl();
        mVideoView.setVideoPath(videoUrl);
        this.currentVideo = bean;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            new Download_GIF(currentVideo.getVideourl()).execute();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private boolean checkPermissionForStorage() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    public class Download_GIF extends AsyncTask<String, int[], String> {

        private final int current = 0;
        String url_image = null;
        private ProgressDialog progressDialog;

        public Download_GIF(String url_image) {
            this.url_image = url_image;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(VideoDetailScreen.this);
            progressDialog.setTitle("Downloading File...");
            progressDialog.setMax(0);
            progressDialog.setProgress(0);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(int[]... values) {
            super.onProgressUpdate(values);
            int[] max = values[0];
            progressDialog.setMax(max[0]);
            progressDialog.setProgress(max[1]);
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
                String filename = "video."+url_image.substring(url_image.lastIndexOf("."));   // you can download to any type of file ex:.jpeg (image) ,.txt(text file),.mp3 (audio file)
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
                    int [] values = {totalSize,downloadedSize};
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
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            whatsappIntent.setType("*/*");
//            String packageName = Utility.getPackageName(current);
//            whatsappIntent.setPackage(packageName);
            Uri photoURI = FileProvider.getUriForFile(VideoDetailScreen.this, getApplicationContext().getPackageName() + ".fileprovider", new File(s));
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            whatsappIntent.setAction(ACTION_SEND);
            startActivity(Intent.createChooser(whatsappIntent, "sharing video file"));
            /*try {
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
            }*/
        }
    }
}
