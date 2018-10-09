package com.hunter.videostatus.ui.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.hunter.videostatus.BuildConfig;
import com.hunter.videostatus.R;
import com.hunter.videostatus.ui.fragments.GifFragment;
import com.hunter.videostatus.ui.fragments.TabFragment;
import com.hunter.videostatus.vidstatus.Constant;

import java.util.Timer;

public class TabActivity extends AppCompatActivity {

    Toolbar toolbar;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private ActionMode mActionMode;
    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitive_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.header_color));
        }

        MobileAds.initialize(this, BuildConfig.DEBUG?Constant.TEST_INIT_ID:Constant.AD_ID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setTitle(getTitleText(0));
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();
        if (!checkPermissionForStorage()) {
            requestPermission();
        }

      /*  mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private boolean checkPermissionForStorage() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private String getTitleText(int i) {

        switch (i) {
            case 1:
                return "Video Status";
            case 2:
                return "Text Status";
            case 3:
                return "Image Status";
            case 4:
                return "Gif Status";
            default:
                return "Video Status";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    private boolean isExiting = false;

    @Override
    public void onBackPressed() {
        if (isExiting) {
            super.onBackPressed();
            return;
        }
        isExiting = true;
        Toast.makeText(TabActivity.this, "Press again to exit!!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isExiting = false;
            }
        }, 1000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.video_status) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            TabFragment tab = new TabFragment();
            tab.addTag("Video");
            toolbar.setTitle(getTitleText(1));
            fragmentTransaction.replace(R.id.frame_container, tab).commit();
        }

        if (menuItem.getItemId() == R.id.text_status) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            TabFragment tab = new TabFragment();
            tab.addTag("Text");
            toolbar.setTitle(getTitleText(2));
            fragmentTransaction.replace(R.id.frame_container, tab).commit();
        }

        if (menuItem.getItemId() == R.id.image_status) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            TabFragment tab = new TabFragment();
            tab.addTag("Image");
            toolbar.setTitle(getTitleText(3));
            fragmentTransaction.replace(R.id.frame_container, tab).commit();
        }

        if (menuItem.getItemId() == R.id.gif_status) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            GifFragment tab = new GifFragment();
            toolbar.setTitle(getTitleText(4));
            fragmentTransaction.replace(R.id.frame_container, tab).commit();
        }

        return true;
    }
}
