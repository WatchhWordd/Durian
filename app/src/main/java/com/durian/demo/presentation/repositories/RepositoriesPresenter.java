package com.durian.demo.presentation.repositories;

import android.content.Context;

import com.durian.demo.domain.usecase.GetRepositoryList;

/**
 * @author zhangyb
 * @description presenter
 * @date 2017/11/9
 */

public class RepositoriesPresenter implements RepositoriesContract.Presenter {

    private Context context;
    private RepositoriesContract.View repoView;

    public RepositoriesPresenter(Context context, RepositoriesContract.View view, GetRepositoryList getRepositoryList) {
        this.context = context;
        this.repoView = view;
        this.repoView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
