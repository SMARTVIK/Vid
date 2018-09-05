package com.hunter.videostatus.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.InterstitialAd;
import com.hunter.videostatus.R;
import com.hunter.videostatus.adapter.GifAdapter;
import com.hunter.videostatus.gifandvideos.GifCategories;
import com.hunter.videostatus.gifandvideos.MainPojoGIF;
import com.hunter.videostatus.listeners.OnItemClickListener;
import com.hunter.videostatus.ui.activity.MainActivity;
import com.hunter.videostatus.ui.activity.SpacesItemDecoration;
import com.hunter.videostatus.util.Utility;
import com.hunter.videostatus.vidstatus.Api;
import com.hunter.videostatus.vidstatus.StatusApi;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GifFragment extends Fragment implements OnItemClickListener {

    private View mRootView;
    private GifAdapter gifAdapter;
    private List<GifCategories.DataBean> dataBeans;
    private InterstitialAd mInterstitialAd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_content, container, false);
        setUpAdd();
        setUpList();
        getGifCategories();
        return mRootView;
    }

    private void setUpAdd() {
        /*mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3748283843614648/4463718103");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });*/
    }

    private void getGifCategories() {
        Utility.showLoader(mRootView);
        Api api = new StatusApi().getGifApi();
        final Call<GifCategories> req = api.getGIFcategory();
        req.enqueue(new Callback<GifCategories>() {
            @Override
            public void onResponse(Call<GifCategories> call, Response<GifCategories> response) {
                Log.d("onResponse", response.isSuccessful() + "");
                dataBeans = response.body().getData();
                Collections.shuffle(dataBeans);
                gifAdapter.setData(dataBeans);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utility.hideLoader(mRootView);
                    }
                }, 500);
            }

            @Override
            public void onFailure(Call<GifCategories> call, Throwable t) {
                Log.d("onFailure", t.getMessage() + "");
                Utility.hideLoader(mRootView);
            }
        });
    }

    private void showLoader() {
        mRootView.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.rv_latest_video_status).setVisibility(View.GONE);
    }

    private void hideLoader() {
        mRootView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        mRootView.findViewById(R.id.rv_latest_video_status).setVisibility(View.VISIBLE);
    }

    private void setUpList() {
        RecyclerView gifList = mRootView.findViewById(R.id.rv_latest_video_status);
        gifList.addItemDecoration(new SpacesItemDecoration(10));
        GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(getContext(), 2);
        gifList.setLayoutManager(staggeredGridLayoutManager);
        gifAdapter = new GifAdapter(getContext());
        gifAdapter.setOnItemClickListener(this);
        gifList.setAdapter(gifAdapter);
    }

    @Override
    public void onItemClick(GifCategories.DataBean dataBean) {
        /*if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }*/
        startActivity(new Intent(getContext(), MainActivity.class).putExtra("cat_id", dataBean.getCat_id()).putExtra("cat_name", dataBean.getCat_name()));
    }
}
