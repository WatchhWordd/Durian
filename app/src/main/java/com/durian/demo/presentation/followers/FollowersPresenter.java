package com.durian.demo.presentation.followers;

import android.content.Context;

import com.durian.demo.domain.usecase.GetUserFollowers;

/**
 * @author zhangyb
 * @description presenter
 * @date 2017/11/10
 */

public class FollowersPresenter implements FollowersContract.Presenter {
    private Context context;
    private FollowersContract.View followerView;

    private GetUserFollowers getUserFollowers;

    public FollowersPresenter(Context context, FollowersContract.View view, GetUserFollowers getUserFollowers) {
        this.context =context;
        this.followerView = view;
        this.getUserFollowers = getUserFollowers;
        followerView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
