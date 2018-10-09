package com.hunter.videostatus.ui.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hunter.videostatus.BuildConfig;
import com.hunter.videostatus.R;
import com.hunter.videostatus.ui.fragments.GetContentByCategoryId;
import com.hunter.videostatus.vidstatus.Constant;

public class ContentByCategory extends AppCompatActivity {

    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_by_cat);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.header_color));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        loadAd();
        String catString = getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(getIntent().getStringExtra("cat_name"));
        String catId = getIntent().getStringExtra("cat_id");
        GetContentByCategoryId catByIdFragment = new GetContentByCategoryId();
        catByIdFragment.addTag(catString, catId);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, catByIdFragment, null).commit();
    }

    private void loadAd() {
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
