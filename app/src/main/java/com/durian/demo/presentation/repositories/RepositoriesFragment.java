package com.durian.demo.presentation.repositories;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.durian.demo.BaseFragment;
import com.durian.demo.GitDataInjection;
import com.durian.demo.R;

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

    private String userName;
    private RepositoriesContract.Presenter presenter;

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
        initSwipeLayout(view);
        initRecycleLayout(view);
    }

    private void initSwipeLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.id_repo_swipe_container);
    }

    private void initRecycleLayout(View view) {
        recyclerView = view.findViewById(R.id.id_repo_recycler_view);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            userName = getArguments().getString(ARG_PARAM_USERNAME);
        }
        new RepositoriesPresenter(context, this, GitDataInjection.provideGetReositoryList());
        presenter.start();
    }

    @Override
    public void setPresenter(RepositoriesContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
