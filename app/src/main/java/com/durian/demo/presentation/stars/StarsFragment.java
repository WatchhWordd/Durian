package com.durian.demo.presentation.stars;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.durian.demo.BaseFragment;
import com.durian.demo.GitDataInjection;
import com.durian.demo.R;
import com.durian.demo.base.widget.MarginDecoration;
import com.durian.demo.base.widget.RippleItemAnimator;
import com.durian.demo.data.net.bean.ReposInfo;
import com.durian.demo.presentation.stars.adapter.StarsAdapter;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/10
 */

public class StarsFragment extends BaseFragment implements StarsContract.View {

    private static final String USERNAME = "username";
    private static final String PARAMS = "params";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private String userName;
    private StarsContract.Presenter presenter;
    private StarsAdapter starsAdapter;
    private ArrayList<ReposInfo> reposInfos;

    public static StarsFragment newInstance(String login, String subject) {
        StarsFragment starsFragment = new StarsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USERNAME, login);
        bundle.putString(PARAMS, subject);
        starsFragment.setArguments(bundle);
        return starsFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_stars;
    }

    @Override
    public void initView(View view) {
        reposInfos = new ArrayList<>();
        reposInfos.clear();
        initSwipeLayout(view);
        initRecycleLayout(view);
    }

    private void initSwipeLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.id_stars_swipe_container);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(()->{
            swipeRefreshLayout.setRefreshing(true);
            loadRepo();
        });
    }

    private void loadRepo() {
        reposInfos.clear();
        presenter.loadData(userName);
    }

    private void initRecycleLayout(View view) {
        recyclerView = view.findViewById(R.id.id_stars_recycler_view);
        recyclerView.addItemDecoration(new MarginDecoration(context));
        recyclerView.setItemAnimator(new RippleItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));
        starsAdapter = new StarsAdapter(context,reposInfos);
        recyclerView.setAdapter(starsAdapter);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            userName = getArguments().getString(USERNAME);
        }
        new StarsPresenter(context, userName,this, GitDataInjection.provideGetStarsList());
        presenter.start();
    }

    @Override
    public void setPresenter(StarsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showDataListView(ArrayList<ReposInfo> reposInfos) {
        swipeRefreshLayout.setRefreshing(false);
        this.reposInfos.addAll(reposInfos);
        starsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDataFail(String fail) {
        swipeRefreshLayout.setRefreshing(false);

    }
}
