package com.durian.demo.presentation.following;

import android.content.Context;

import com.durian.demo.domain.usecase.GetUserFollowings;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhangyb
 * @description presenter
 * @date 2017/11/10
 */

public class FollowingPresenter implements FollowingContract.Presenter {


    private Context context;
    private FollowingContract.View followingView;
    private GetUserFollowings loadUserFollowings;

    public FollowingPresenter(Context context, FollowingContract.View view, GetUserFollowings getUserFollowings) {
        this.context = context;
        this.followingView = view;
        this.loadUserFollowings =getUserFollowings;
        followingView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(String userName) {
        GetUserFollowings.Request request= new GetUserFollowings.Request(userName);
        loadUserFollowings.executeUseCase(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetUserFollowings.Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetUserFollowings.Response response) {
                        if (followingView!=null){
                            followingView.showDataListView(response.getUserInfo());
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
