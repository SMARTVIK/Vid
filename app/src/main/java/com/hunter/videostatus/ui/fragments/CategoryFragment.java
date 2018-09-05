package com.hunter.videostatus.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.hunter.videostatus.R;
import com.hunter.videostatus.adapter.CategoryAdapter;
import com.hunter.videostatus.listeners.EndlessRecyclerViewScrollListener;
import com.hunter.videostatus.listeners.OnItemClick;
import com.hunter.videostatus.model.CategoryModel;
import com.hunter.videostatus.ui.activity.ContentByCategory;
import com.hunter.videostatus.ui.activity.SpacesItemDecoration;
import com.hunter.videostatus.util.Utility;
import com.hunter.videostatus.vidstatus.Api;
import com.hunter.videostatus.vidstatus.StatusApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment implements OnItemClick {

    private static final String TAG = TrendingContentFragment.class.getSimpleName();
    private String catString;
    private RecyclerView listOfItems;
    private InterstitialAd mInterstitialAd;

    public CategoryFragment() {
        // Required empty public constructor
    }
    private View mRootView;
    private CategoryAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private List<CategoryModel.DataBean> dataBeans = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_content, container, false);
        initViews(mRootView);
        getLatestVideoStatus(1);
        /*mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3748283843614648/4463718103");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });*/
        return mRootView;
    }

    private void getLatestVideoStatus(int page) {
        Utility.showLoader(mRootView);
        Api api = new StatusApi().getStatusApi();
        final Call<CategoryModel> req = getCall(api);
        req.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                Log.d("onResponse", response.isSuccessful() + "");
                /*if (dataBeans.isEmpty()) {
                    dataBeans = response.body().getData();
                } else {
                    dataBeans.addAll(response.body().getData());
                }*/
                Utility.hideLoader(mRootView);
                dataBeans = response.body().getData();
                adapter.setData(dataBeans);
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Log.d("onFailure", t.getMessage() + "");
                Utility.hideLoader(mRootView);
            }
        });
    }

    private Call<CategoryModel> getCall(Api api) {

        Call<CategoryModel> req = null;

        switch (catString) {

            case "Video":
                req = api.getVideoCatigories();
                break;

            case "Text":
                req = api.getTextCatigories();
                break;

            case "Image":
                req = api.getImageCatigories();
                break;

            case "Gif":
                req = api.getImageCatigories();
                break;

        }
        return req;
    }

    private void initViews(View v) {
        listOfItems = v.findViewById(R.id.rv_latest_video_status);
        listOfItems.addItemDecoration(new SpacesItemDecoration(10));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listOfItems.setLayoutManager(linearLayoutManager);
        adapter = new CategoryAdapter();
        adapter.setOnItemClickListerner(this);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int page, int totalItemsCount, RecyclerView view) {
                Log.d(TAG, "page number " + page);
                listOfItems.post(new Runnable() {
                    @Override
                    public void run() {
                        getLatestVideoStatus(page);
                    }
                });
            }
        };
        listOfItems.setAdapter(adapter);
        listOfItems.addOnScrollListener(scrollListener);
    }

    public void addTag(String string) {
        this.catString = string;
    }

    @Override
    public void onItemClick(CategoryModel.DataBean dataBean) {
        /*if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }*/
        startActivity(new Intent(getContext(), ContentByCategory.class).putExtra("cat_id", dataBean.getCategory_id())
                                                                       .putExtra("category", catString)
                                                                       .putExtra("cat_name",dataBean.getCategory_name()));
    }
}
