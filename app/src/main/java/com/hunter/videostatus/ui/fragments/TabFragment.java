package com.hunter.videostatus.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.videostatus.R;

import java.util.ArrayList;


public class TabFragment extends Fragment {

    public TabLayout tabLayout;
    public ViewPager viewPager;
    private String catString = "Video";

    private CategoryFragment categoryFragment = null;
    private LatestContentFragment latestFragment = null;
    private TrendingContentFragment trendingFragment = null;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_layout, container, false);
        addFragmentsToList();
        initViews(v);
        return v;
    }

    private void addFragmentsToList() {
        categoryFragment = new CategoryFragment();
        categoryFragment.addTag(catString);
        latestFragment = new LatestContentFragment();
        latestFragment.addTag(catString);
        trendingFragment = new TrendingContentFragment();
        trendingFragment.addTag(catString);

        fragments.add(categoryFragment);
        fragments.add(latestFragment);
        fragments.add(trendingFragment);
    }

    private void initViews(View v) {
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    public void addTag(String string) {
        this.catString = string;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return getResources().getString(R.string.tab1);
                case 1:
                    return getResources().getString(R.string.tab2);
                case 2:
                    return getResources().getString(R.string.tab3);
            }
            return null;
        }
    }
}

