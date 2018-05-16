package com.durian.demo.base.net;

import com.durian.demo.base.RxUseCase;

import io.reactivex.Observable;

/**
 * @author zhangyb
 * @description
 * @date 2018/5/16
 */
public class IsNetConnection extends RxUseCase<Void, Boolean> {

    private NetDataSource netDataSource;

    public IsNetConnection(NetDataSource netDataSource) {
        this.netDataSource = netDataSource;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(Void requestValues) {
        return netDataSource.isConnectedAsSingle().toObservable();
    }
}
