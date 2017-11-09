package com.durian.demo.presentation.repositories;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.durian.demo.BaseFragment;
import com.durian.demo.R;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/9
 */

public class RepositoriesFragment extends BaseFragment {

    private static final String ARG_PARAM_USERNAME = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

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
        recyclerView = view.findViewById(R.id.id_repo_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.id_repo_swipe_container);
    }

    @Override
    public void initData() {

    }
}
