package com.durian.demo.base.net;

import com.durian.demo.base.RxUseCase;

import io.reactivex.Observable;

/**
 * @author zhangyb
 * @description 网络变化usecase
 * @date 2018/5/16
 */
public class SubscribeNetState extends RxUseCase<Void, NetState> {

    private NetDataSource netDataSource;

    public SubscribeNetState(NetDataSource netDataSource) {
        this.netDataSource = netDataSource;
    }

    @Override
    protected Observable<NetState> buildUseCaseObservable(Void requestValues) {
        return netDataSource.getNetStateObserver();
    }
}
