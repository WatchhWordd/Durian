package com.durian.demo.presentation.overview;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.durian.demo.BaseFragment;
import com.durian.demo.R;
import com.durian.demo.base.utils.DateUtil;
import com.durian.demo.base.utils.RxBus;
import com.durian.demo.base.utils.ScreenUtil;
import com.durian.demo.base.widget.MarginDecoration;
import com.durian.demo.data.net.bean.LoadParam;
import com.durian.demo.data.net.bean.ReposInfo;
import com.durian.demo.data.net.bean.UserInfo;
import com.durian.demo.presentation.overview.adapter.OverEventAdapter;
import com.durian.demo.presentation.overview.adapter.OverRepoAdapter;
import com.durian.gitlogger.Log;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/8
 */

public class OverViewFragment extends BaseFragment implements OverViewContract.View {

    private static final String URL_CONTRIBUTIONS = "https://github" +
            ".com/users/%1$s/contributions?from=%2$s&to=%3$s&full_graph=1";
    private static final String ARG_USERNAME = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userName;
    private String params;
    private ArrayList<ReposInfo> reposInfos;

    RecyclerView popularRepoRecyclerView;
    RecyclerView overViewEventsRecyclerView;
    SwipeRefreshLayout swipeContainer;
    WebView contributionsWebView;
    private OverRepoAdapter overRepoAdapter;
    private OverEventAdapter overEventAdapter;
    private OverViewContract.Presenter presenter;

    private Disposable disposable;


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
        initRepoRecycleView(view);
        initEventRecycleView(view);
        initSwipeLayoutView(view);
        initWebViewLayoutView(view);
    }

    private void initRegisterPost() {
        disposable = RxBus.getInstance().toFlowable(LoadParam.class)
                .subscribe(new Consumer<LoadParam>() {
                    @Override
                    public void accept(LoadParam result) {
                        Log.getLogger().info("overView=" + result.getType());
                        OverViewFragment.this.refreshOverRepose(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.getLogger().info("overView_error=" + throwable.getCause());
                    }
                });
    }

    private void refreshOverRepose(LoadParam result) {
        if (result.getType() == 0) {
            showRepoesView(result.getReposInfos());
        }
    }

    private void initRepoRecycleView(View view) {
        reposInfos = new ArrayList<>();
        reposInfos.clear();
        popularRepoRecyclerView = view.findViewById(R.id.id_over_view_popular_repo_recycler_view);
        popularRepoRecyclerView.addItemDecoration(new MarginDecoration(context));
        popularRepoRecyclerView.setHasFixedSize(true);
        popularRepoRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        overRepoAdapter = new OverRepoAdapter(context, reposInfos);
        popularRepoRecyclerView.setAdapter(overRepoAdapter);
        popularRepoRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initEventRecycleView(View view) {
        overViewEventsRecyclerView = view.findViewById(R.id.id_over_view_events_recycler_view);
        overViewEventsRecyclerView.addItemDecoration(new MarginDecoration(context));
        overViewEventsRecyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        overEventAdapter = new OverEventAdapter();
        overViewEventsRecyclerView.setAdapter(overEventAdapter);
        overViewEventsRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initSwipeLayoutView(View view) {
        swipeContainer = view.findViewById(R.id.id_over_swipe_container);
        swipeContainer.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeContainer.setOnRefreshListener(() -> {
            swipeContainer.setRefreshing(true);
            loadCurrentData();
        });
    }

    private void initWebViewLayoutView(View view) {
        contributionsWebView = view.findViewById(R.id.id_overview_contribution_web_view);
        contributionsWebView.setVisibility(View.INVISIBLE);
        contributionsWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.setVisibility(View.VISIBLE);
                view.invalidate();
                view.requestLayout();
                view.setInitialScale(getScale());
            }
        });
        contributionsWebView.loadUrl(getContributionsUrl());
    }

    private void loadCurrentData() {
        presenter.refreshOverRepoes();
        presenter.refreshEvents();
        contributionsWebView.reload();
    }


    @Override
    public void initData() {
        initRegisterPost();
        if (getArguments() != null) {
            userName = getArguments().getString(ARG_USERNAME);
            params = getArguments().getString(ARG_PARAM2);
        }

        new OverViewPresenter(context, this);
        presenter.start();
    }

    private String getContributionsUrl() {
        String url = "";
        Date endDate = DateUtil.getLastDayOfMonth();
        String endDateStr = DateUtil.formatDate(endDate, DateUtil.FORMAT_2);
        Date startDate = DateUtil.getFirstDayOfMonth();
        String startDateStr = DateUtil.formatDate(startDate, DateUtil.FORMAT_2);
        if (!TextUtils.isEmpty(userName)) {
            url = String.format(URL_CONTRIBUTIONS, userName, startDateStr, endDateStr);
        }
        return url;
    }

    public int getScale() {
        return ScreenUtil.getWebViewScale(context, 720);
    }

    @Override
    public void setPresenter(OverViewContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showRepoesView(ArrayList<ReposInfo> reposInfos) {
        swipeContainer.setRefreshing(false);
        this.reposInfos.clear();
        this.reposInfos.addAll(reposInfos);
        overRepoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFollowerView(ArrayList<UserInfo> userInfos) {

    }

    @Override
    public void showFollowingView(ArrayList<UserInfo> userInfos) {

    }

    @Override
    public void showStarsView(ArrayList<ReposInfo> reposInfos) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (RxBus.getInstance().hasObservers()) {
            RxBus.getInstance().removeRxBus(disposable);
        }
    }
}
