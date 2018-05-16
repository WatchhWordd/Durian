package com.durian.demo.base.net;

import android.content.Context;

/**
 * @author zhangyb
 * @description 获取网络状态
 * @date 2018/5/16
 */
public class NetDataInjection {

    public static SubscribeNetState provideSubscribeNetState(Context context) {
        return new SubscribeNetState(provideNetStateDataSource(context));
    }

    public static NetDataSource provideNetStateDataSource(Context context) {
        return provideNetStateRepository(context);
    }

    public static NetStateRepository provideNetStateRepository(Context context) {
        return NetStateRepository.getInstance(context);
    }

    public static GetNetInfo provideGetNetState(Context context) {
        return new GetNetInfo(provideNetStateDataSource(context));
    }

    public static IsNetConnection provideIsNetConnected(Context context) {
        return new IsNetConnection(provideNetStateDataSource(context));
    }
}
