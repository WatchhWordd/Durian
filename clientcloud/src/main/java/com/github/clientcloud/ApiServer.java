package com.github.clientcloud;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;

/**
 * @author zhangyb
 * @description 网络配置参数
 * @date 2017/9/15
 */

public abstract class ApiServer {

    public interface Config {

        String APP_ID = "app_id";
        String APP_KEY = "app_key";
        String CONTENT_TYPE = "content_type";
        String APP_VERSION = "appVersion";
        String ACCESS_TOKEN = "accessToken";
        String LANGUAGE = "language";
        String TIMEZONE = "timeZone";
    }

    public OkHttpClient.Builder createOkHttpBuilder(Context context) {
        return Observable.fromIterable(getIniterList())
                .filter(initer -> initer instanceof OkhttpIniter)
                .cast(OkhttpIniter.class)
                .reduce(new OkHttpClient.Builder(),
                        (builder, initer) -> initer.initialize(builder, this, context))
                .blockingGet();
    }

    public OkHttpClient.Builder onOkHttpBuilderCreated(OkHttpClient.Builder builder, Context context) {
        return builder;
    }

    public abstract List<String> getBaseUrlList();

    public abstract List<Initer> getIniterList();
}
