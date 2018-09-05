package com.hunter.videostatus.vidstatus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.hunter.videostatus.R;
import com.hunter.videostatus.model.Status;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoStatusActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_status);
        getLatestVideoStatus();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(android.R.drawable.ic_menu_preferences);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.view_pager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                getLatestVideoStatus();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getLatestVideoStatus() {
        Api api = new StatusApi().getStatusApi();
        Call<Status> req = api.getLatestVideoStatus(1);
        req.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Log.d("onResponse", response.isSuccessful() + "");


            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.d("onFailure", t.getMessage() + "");
            }
        });
    }
}
