package com.durian.demo.presentation.stars;

import android.content.Context;

import com.durian.demo.domain.usecase.GetStarsList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhangyb
 * @description presenter
 * @date 2017/11/10
 */

public class StarsPresenter implements StarsContract.Presenter {

    private Context context;
    private StarsContract.View starsView;
    private GetStarsList loadStarsList;

    public StarsPresenter(Context context, StarsContract.View view, GetStarsList getStarsList) {
        this.context = context;
        this.starsView = view;
        this.loadStarsList = getStarsList;
        starsView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(String userName) {
        GetStarsList.Request request = new GetStarsList.Request(userName);
        loadStarsList.executeUseCase(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetStarsList.Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetStarsList.Response response) {

                        if (starsView!=null){
                            starsView.showDataListView(response.getReposInfos());
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
