package com.durian.demo.presentation.overview;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.durian.demo.BaseFragment;
import com.durian.demo.R;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/8
 */

public class OverViewFragment extends BaseFragment {

    private static final String ARG_USERNAME = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userName;
    private String params;

    RecyclerView popularRepoRecyclerView;
    RecyclerView overViewEventsRecyclerView;
    SwipeRefreshLayout swipeContainer;
    WebView mContributionsWebView;

    public static OverViewFragment newInstance(String param1, String param2) {
        OverViewFragment fragment = new OverViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_over_view;
    }

    @Override
    public void initView(View view) {
        popularRepoRecyclerView = view.findViewById(R.id.id_over_view_popular_repo_recycler_view);
        mContributionsWebView = view.findViewById(R.id.id_overview_contribution_web_view);
        overViewEventsRecyclerView = view.findViewById(R.id.id_over_view_events_recycler_view);
        swipeContainer = view.findViewById(R.id.id_over_swipe_container);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            userName = getArguments().getString(ARG_USERNAME);
            params = getArguments().getString(ARG_PARAM2);
        }
    }
}
