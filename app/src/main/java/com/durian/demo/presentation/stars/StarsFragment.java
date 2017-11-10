package com.durian.demo.presentation.stars;

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
 * @date 2017/11/10
 */

public class StarsFragment extends BaseFragment implements StarsContract.View {

    private static final String USERNAME = "username";
    private static final String PARAMS = "params";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private String userName;
    private StarsContract.Presenter presenter;

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
        initSwipeLayout(view);
        initRecycleLayout(view);
    }

    private void initSwipeLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.id_stars_swipe_container);
    }

    private void initRecycleLayout(View view) {
        recyclerView = view.findViewById(R.id.id_stars_recycler_view);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            userName = getArguments().getString(USERNAME);
        }
        new StarsPresenter(context, this, GitDataInjection.provideGetStarsList());
        presenter.start();
    }

    @Override
    public void setPresenter(StarsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
