package com.hunter.videostatus;

import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

public class DetailScreen extends AppCompatActivity {
    private static final String TAG = DetailScreen.class.getSimpleName();
    View mBottomLayout;
    View mVideoLayout;
    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;
    private boolean isFullscreen;
    private int cachedHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        MobileAds.initialize(this, "ca-app-pub-3748283843614648~4916776186");
        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {
                DetailScreen.this.isFullscreen = isFullscreen;
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
                    layoutParams.height = DetailScreen.this.cachedHeight;
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
    }

    private void addViewInit() {
        /*String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(android_id)  // An example device ID
                .build();*/
    }
}
