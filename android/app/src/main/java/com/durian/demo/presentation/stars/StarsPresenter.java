package com.durian.demo.presentation.stars;

import android.content.Context;

import com.durian.demo.base.utils.ACache;
import com.durian.demo.base.utils.ConfigUtil;
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
    private String userName;

    public StarsPresenter(Context context, String userName, StarsContract.View view, GetStarsList getStarsList) {
        this.context = context;
        this.userName = userName;
        this.starsView = view;
        this.loadStarsList = getStarsList;
        starsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadData(userName);
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
                        if (starsView != null) {
                            starsView.showDataListView(response.getReposInfos());
                        }
                        ACache.get(context).put(ConfigUtil.S_STARRED,response.getReposInfos());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (starsView != null) {
                             starsView.showDataFail(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
