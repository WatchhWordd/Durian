package com.durian.demo.presentation.repositories;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.durian.demo.BaseFragment;
import com.durian.demo.GitDataInjection;
import com.durian.demo.R;
import com.durian.demo.base.widget.MarginDecoration;
import com.durian.demo.base.widget.RippleItemAnimator;
import com.durian.demo.data.net.bean.ReposInfo;
import com.durian.demo.presentation.repositories.adpter.RepositoriesAdapter;
import com.durian.gitlogger.Log;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/9
 */

public class RepositoriesFragment extends BaseFragment implements RepositoriesContract.View {

    private static final String ARG_PARAM_USERNAME = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RepositoriesAdapter repositoriesAdapter;

    private String userName;
    private RepositoriesContract.Presenter presenter;
    private ArrayList<ReposInfo> reposInfos;

    public static RepositoriesFragment newInstance(String username, String param2) {
        RepositoriesFragment repositoriesFragment = new RepositoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_USERNAME, username);
        args.putString(ARG_PARAM2, param2);
        repositoriesFragment.setArguments(args);
        return repositoriesFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_repositories;
    }

    @Override
    public void initView(View view) {
        reposInfos = new ArrayList<>();
        reposInfos.clear();
        initSwipeLayout(view);
        initRecycleLayout(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.getLogger().info("RepositoriesFragment onResume");
    }

    private void initSwipeLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.id_repo_swipe_container);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(()->{
            swipeRefreshLayout.setRefreshing(true);
            loadRepo();
        });
    }

    private void loadRepo() {
        reposInfos.clear();
        presenter.loadRepoData(userName);
    }

    private void initRecycleLayout(View view) {
        recyclerView = view.findViewById(R.id.id_repo_recycler_view);
        recyclerView.addItemDecoration(new MarginDecoration(context));
        recyclerView.setItemAnimator(new RippleItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));
        repositoriesAdapter = new RepositoriesAdapter(context,reposInfos);
        recyclerView.setAdapter(repositoriesAdapter);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            userName = getArguments().getString(ARG_PARAM_USERNAME);
        }
        new RepositoriesPresenter(context,userName, this, GitDataInjection.provideGetReositoryList());
        presenter.start();
    }

    @Override
    public void setPresenter(RepositoriesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showRepoDataList(ArrayList<ReposInfo> reposInfos) {
        swipeRefreshLayout.setRefreshing(false);
        this.reposInfos.addAll(reposInfos);
        repositoriesAdapter.notifyDataSetChanged();

    }

    @Override
    public void showRepoDataFail(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);
      Toast.makeText(context,"load fail",Toast.LENGTH_LONG).show();
    }
}
