package com.durian.demo.base.net;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author zhangyb
 * @description
 * @date 2018/5/16
 */
public interface NetDataSource {

    Context getContext();

    NetState getNetInfo();

    String getIp();

    boolean isConnected();

    Single<Boolean> isConnectedAsSingle();

    Observable<NetState> getNetStateObserver();

    Single<NetState> getNetStateSingle();
}
