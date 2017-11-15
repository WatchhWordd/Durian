package com.durian.demo.presentation.followers;

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
import com.durian.demo.data.net.bean.UserInfo;
import com.durian.demo.presentation.followers.adapter.FollowersAdapter;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/10
 */

public class FollowersFragment extends BaseFragment implements FollowersContract.View {

    private static final String USERNAME = "username";
    private static final String PARAMS = "params";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private String userName;
    private FollowersContract.Presenter presenter;
    private FollowersAdapter followersAdapter;
    private ArrayList<UserInfo> userInfos;

    public static FollowersFragment newInstance(String login, String subject) {
        FollowersFragment followersFragment = new FollowersFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USERNAME, login);
        bundle.putString(PARAMS, subject);
        followersFragment.setArguments(bundle);
        return followersFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_follower;
    }

    @Override
    public void initView(View view) {
        userInfos = new ArrayList<>();
        userInfos.clear();
        initSwipeLayout(view);
        initRecycleLayout(view);
    }

    private void initSwipeLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.id_follower_swipe_container);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            loadRepo();
        });
    }

    private void loadRepo() {
        userInfos.clear();
        presenter.loadData(userName);
    }

    private void initRecycleLayout(View view) {
        recyclerView = view.findViewById(R.id.id_follower_recycler_view);
        recyclerView.addItemDecoration(new MarginDecoration(context));
        recyclerView.setItemAnimator(new RippleItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        followersAdapter = new FollowersAdapter(context, userInfos);
        recyclerView.setAdapter(followersAdapter);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            userName = getArguments().getString(USERNAME);
        }
        new FollowersPresenter(context, userName, this, GitDataInjection.provideGetUserFollowers());
        presenter.start();
    }


    @Override
    public void setPresenter(FollowersContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showDataListView(ArrayList<UserInfo> userInfos) {
        swipeRefreshLayout.setRefreshing(false);
        this.userInfos.addAll(userInfos);
        followersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showloadFail(String fail) {
        swipeRefreshLayout.setRefreshing(false);

    }
}
