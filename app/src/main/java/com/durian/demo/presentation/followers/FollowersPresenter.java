package com.durian.demo.presentation.followers;

import android.content.Context;

import com.durian.demo.domain.usecase.GetUserFollowers;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhangyb
 * @description presenter
 * @date 2017/11/10
 */

public class FollowersPresenter implements FollowersContract.Presenter {
    private Context context;
    private FollowersContract.View followerView;

    private GetUserFollowers loadUserFollowers;

    public FollowersPresenter(Context context, FollowersContract.View view, GetUserFollowers getUserFollowers) {
        this.context =context;
        this.followerView = view;
        this.loadUserFollowers = getUserFollowers;
        followerView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(String userName) {
        GetUserFollowers.Request request = new GetUserFollowers.Request(userName);
        loadUserFollowers.executeUseCase(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetUserFollowers.Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetUserFollowers.Response response) {
                       if (followerView!=null){
                           followerView.showDataListView(response.getUserInfos());
                       }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
