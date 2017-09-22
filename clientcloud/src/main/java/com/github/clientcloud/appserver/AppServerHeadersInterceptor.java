package com.github.clientcloud.appserver;

import android.util.Pair;

import com.github.clientcloud.ApiServer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zhangyb
 * @description
 * @date 2017/9/20
 */

class AppServerHeadersInterceptor implements Interceptor {

    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
    private ApiServer apiServer;
    private int requestSn = 0;

    public AppServerHeadersInterceptor(ApiServer apiServer) {
        this.apiServer = apiServer;
    }

    public AppServerHeadersInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = addCommonHeaders(request);
        return chain.proceed(request);
    }

    private Request addCommonHeaders(Request request) {
        Request.Builder builder = request.newBuilder();
        Set<String> names = request.headers().names();
        String timestamp = TIMESTAMP_FORMAT.format(new Date());
        Observable.fromArray(
                pair("Content-Type", () -> apiServer.getConfig(ApiServer.Config.CONTENT_TYPE)),
                pair("appId", () -> apiServer.getConfig(ApiServer.Config.APP_ID)),
                pair("appVersion", () -> apiServer.getConfig(ApiServer.Config.APP_VERSION)),
                pair("appKey", () -> apiServer.getConfig(ApiServer.Config.APP_KEY)),
                pair("clientId", () -> apiServer.getConfig(ApiServer.Config.CLIENT_ID)),
                pair("accessToken", () -> apiServer.getConfig(ApiServer.Config.ACCESS_TOKEN)),
                pair("sequenceId", () -> generateNextSequenceId(timestamp)),
                pair("timestamp", () -> timestamp))
                .filter(pair -> !names.contains(pair.first))
                .forEach(pair -> builder.addHeader(pair.first, pair.second.call()));
        return builder.build();
    }

    private Pair<String, Callable<String>> pair(String name, Callable<String> func0) {
        return Pair.create(name, func0);
    }

    private String generateNextSequenceId(String timestamp) {
        increaseRequestSerialNumber();
        return String.format(Locale.US, "%s%06d", timestamp, requestSn);
    }

    private void increaseRequestSerialNumber() {
        requestSn++;
        if (requestSn > 999999) {
            requestSn = 0;
        }
    }
}
