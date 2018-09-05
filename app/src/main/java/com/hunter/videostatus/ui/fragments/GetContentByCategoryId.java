package com.hunter.videostatus.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.videostatus.R;
import com.hunter.videostatus.adapter.TrendingContentAdapter;
import com.hunter.videostatus.listeners.EndlessRecyclerViewScrollListener;
import com.hunter.videostatus.model.Status;
import com.hunter.videostatus.ui.activity.SpacesItemDecoration;
import com.hunter.videostatus.util.Utility;
import com.hunter.videostatus.vidstatus.Api;
import com.hunter.videostatus.vidstatus.StatusApi;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetContentByCategoryId extends Fragment {

    private static final String TAG = TrendingContentFragment.class.getSimpleName();
    private String catString;
    private String catId;
    private View mRootView;

    public GetContentByCategoryId() {
        // Required empty public constructor
    }

    private TrendingContentAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ArrayList<Status.DataBean> dataBeans = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_content, container, false);
        initViews(mRootView);
        getContentByCatId(catString, catId, 1);
        return mRootView;
    }

    private void getContentByCatId(final String catString, String catId, int page) {
        if (page == 1) {
            Utility.showLoader(mRootView);
        }
        Api api = new StatusApi().getStatusApi();
        final Call<Status> req = getCall(api, catString, catId, page);
        req.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Log.d("onResponse", response.isSuccessful() + "");
                Utility.hideLoader(mRootView);
                if (dataBeans.isEmpty()) {
                    dataBeans = response.body().getData();
                } else {
                    dataBeans.addAll(response.body().getData());
                }
                adapter.setData(dataBeans);
                adapter.setType(catString);
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.d("onFailure", t.getMessage() + "");
                Utility.hideLoader(mRootView);
            }
        });
    }

    private Call<Status> getCall(Api api, String catString, String catId, int page) {
        Call<Status> request = null;
        switch (catString) {
            case "Video":
                request = api.getTrendingVideoStatusByCat(page, catId);
                break;
            case "Text":
                request = api.getTrendingTextStatusByCat(page, catId);
                break;
            case "Image":
                request = api.getTrendingImageStatusByCat(page, catId);
                break;
        }
        return request;
    }

    private void initViews(View v) {
        final RecyclerView recyclerView = v.findViewById(R.id.rv_latest_video_status);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TrendingContentAdapter(getContext());
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int page, int totalItemsCount, RecyclerView view) {
                Log.d(TAG, "page number " + page);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        getContentByCatId(catString, catString, page);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(scrollListener);
    }

    public void addTag(String catString, String string) {
        this.catId = string;
        this.catString = catString;
    }


}
