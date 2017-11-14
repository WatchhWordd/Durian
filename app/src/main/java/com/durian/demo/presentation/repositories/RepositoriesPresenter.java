package com.durian.demo.presentation.repositories;

import android.content.Context;

import com.durian.demo.domain.usecase.GetRepositoryList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhangyb
 * @description presenter
 * @date 2017/11/9
 */

public class RepositoriesPresenter implements RepositoriesContract.Presenter {

    private Context context;
    private RepositoriesContract.View repoView;
    private GetRepositoryList loadRepositoryList;
    private CompositeDisposable compositeDisposable;
    private String userName;

    public RepositoriesPresenter(Context context,String userName,
                                 RepositoriesContract.View view, GetRepositoryList getRepositoryList) {
        this.context = context;
        this.userName = userName;
        this.repoView = view;
        this.loadRepositoryList = getRepositoryList;
        this.repoView.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void start() {
        loadRepoData(userName);
    }

    @Override
    public void loadRepoData(String userName) {
        GetRepositoryList.Request request = new GetRepositoryList.Request(userName,"all","full_name","asc");
        loadRepositoryList.executeUseCase(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetRepositoryList.Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(GetRepositoryList.Response response) {
                        if (repoView != null) {
                            repoView.showRepoDataList(response.getReposInfos());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (repoView != null) {
                            repoView.showRepoDataFail(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.clear();
                    }
                });
    }
}
