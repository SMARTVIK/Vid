package com.hunter.videostatus.ui.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hunter.videostatus.R;
import com.hunter.videostatus.ui.fragments.GetContentByCategoryId;

public class ContentByCategory extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_by_cat);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.header_color));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String catString = getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(getIntent().getStringExtra("cat_name"));
        String catId = getIntent().getStringExtra("cat_id");
        GetContentByCategoryId catByIdFragment = new GetContentByCategoryId();
        catByIdFragment.addTag(catString, catId);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,catByIdFragment,null).commit();
    }
}
