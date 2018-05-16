package com.durian.demo.base.net;

import com.durian.demo.base.RxUseCase;

import io.reactivex.Observable;

/**
 * @author zhangyb
 * @description
 * @date 2018/5/16
 */
public class GetNetInfo extends RxUseCase<Void, NetState> {

    private NetDataSource netDataSource;

    public GetNetInfo(NetDataSource netDataSource) {
        this.netDataSource = netDataSource;
    }

    @Override
    protected Observable<NetState> buildUseCaseObservable(Void requestValues) {
        return netDataSource.getNetStateSingle().toObservable();
    }
}
