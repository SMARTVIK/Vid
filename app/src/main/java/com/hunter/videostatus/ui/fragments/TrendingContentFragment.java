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
import com.hunter.videostatus.ui.activity.SpacesItemDecoration;
import com.hunter.videostatus.util.Utility;
import com.hunter.videostatus.vidstatus.Api;
import com.hunter.videostatus.model.Status;
import com.hunter.videostatus.vidstatus.StatusApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrendingContentFragment extends Fragment {

    private static final String TAG = TrendingContentFragment.class.getSimpleName();
    private String catString;
    private RecyclerView list;
    private View mRootView;

    public TrendingContentFragment() {
        // Required empty public constructor
    }

    private TrendingContentAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ArrayList<Status.DataBean> dataBeans = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_content, container, false);
        initViews(mRootView);
        getLatestVideoStatus(1);
        return mRootView;
    }

    private void getLatestVideoStatus(int page) {
        if (page == 1) {
            Utility.showLoader(mRootView);
        }
        Api api = new StatusApi().getStatusApi();
        final Call<Status> req = getCall(api, page);
        req.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Log.d("onResponse", response.isSuccessful() + "");
                if (dataBeans.isEmpty()) {
                    dataBeans = response.body().getData();
                } else {
                    dataBeans.addAll(response.body().getData());
                }
                Utility.hideLoader(mRootView);
                adapter.setType(catString);
                adapter.setData(dataBeans);
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.d("onFailure", t.getMessage() + "");
                Utility.hideLoader(mRootView);
            }
        });
    }

    private Call<Status> getCall(Api api, int page) {

        Call<Status> req = null;

        switch (catString) {

            case "Video":
                req = api.getTrendingVideoStatus(page);
                break;

            case "Text":
                req = api.getTrendingTextStatus(page);
                break;

            case "Image":
                req = api.getTrendingImageStatus(page);
                break;

        }

        return req;
    }

    private void initViews(View v) {
        list = v.findViewById(R.id.rv_latest_video_status);
        if (!catString.equals("Text")) {
            list.addItemDecoration(new SpacesItemDecoration(10));
        } else {
            list.addItemDecoration(new SpacesItemDecoration(0));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(linearLayoutManager);
        adapter = new TrendingContentAdapter(getContext());
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int page, int totalItemsCount, RecyclerView view) {
                Log.d(TAG, "page number " + page);
                list.post(new Runnable() {
                    @Override
                    public void run() {
                        getLatestVideoStatus(page);
                    }
                });
            }
        };
        list.setAdapter(adapter);
        list.addOnScrollListener(scrollListener);
    }

    public void addTag(String catString) {
        this.catString = catString;
        if (list != null) {
            if (!catString.equals("Text")) {
                list.addItemDecoration(new SpacesItemDecoration(10));
            } else {
                list.addItemDecoration(new SpacesItemDecoration(0));
            }
        }
    }
}
