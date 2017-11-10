package com.durian.demo.presentation.following;

import android.content.Context;

import com.durian.demo.domain.usecase.GetUserFollowings;

/**
 * @author zhangyb
 * @description presenter
 * @date 2017/11/10
 */

public class FollowingPresenter implements FollowingContract.Presenter {


    private Context context;
    private FollowingContract.View followingView;
    private GetUserFollowings getUserFollowings;

    public FollowingPresenter(Context context, FollowingContract.View view, GetUserFollowings getUserFollowings) {
        this.context = context;
        this.followingView = view;
        this.getUserFollowings =getUserFollowings;
        followingView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
