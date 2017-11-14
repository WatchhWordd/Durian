package com.durian.demo.presentation.following;

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
import com.durian.demo.presentation.following.adapter.FollowingAdapter;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/10
 */

public class FollowingFragment extends BaseFragment implements FollowingContract.View {
    private static final String USERNAME = "username";
    private static final String PARAMS = "params";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private String userName;
    private FollowingContract.Presenter presenter;
    private FollowingAdapter followingAdapter;
    private ArrayList<UserInfo> userInfos;


    public static FollowingFragment newInstance(String login, String subject) {
        FollowingFragment followingFragment = new FollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USERNAME, login);
        bundle.putString(PARAMS, subject);
        followingFragment.setArguments(bundle);
        return followingFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_following;
    }

    @Override
    public void initView(View view) {
        userInfos = new ArrayList<>();
        userInfos.clear();
        initSwipeLayout(view);
        initRecycleLayout(view);
    }

    private void initSwipeLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.id_following_swipe_container);
        swipeRefreshLayout.setOnRefreshListener(()->{
            swipeRefreshLayout.setRefreshing(true);
            loadRepo();
        });
    }

    private void loadRepo() {
       presenter.loadData(userName);
    }

    private void initRecycleLayout(View view) {
        recyclerView = view.findViewById(R.id.id_following_recycler_view);
        recyclerView.addItemDecoration(new MarginDecoration(context));
        recyclerView.setItemAnimator(new RippleItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));
        followingAdapter = new FollowingAdapter(context,userInfos);
        recyclerView.setAdapter(followingAdapter);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            userName = getArguments().getString(USERNAME);
        }
        new FollowingPresenter(context, this, GitDataInjection.provideGetUserFollowing());
        presenter.start();
    }


    @Override
    public void setPresenter(FollowingContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showDataListView(ArrayList<UserInfo> userInfos) {
        swipeRefreshLayout.setRefreshing(false);
        this.userInfos.addAll(userInfos);
        followingAdapter.notifyDataSetChanged();
    }

    @Override
    public void showloadFail(String fail) {
        swipeRefreshLayout.setRefreshing(false);

    }
}
