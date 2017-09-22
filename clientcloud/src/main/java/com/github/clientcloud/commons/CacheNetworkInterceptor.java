package com.github.clientcloud.commons;

import android.content.Context;

import com.github.clientcloud.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zhangyb
 * @description 网络请求状态
 * @date 2017/9/20
 */

class CacheNetworkInterceptor implements Interceptor {

    private Context context;

    public CacheNetworkInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!request.headers().names().contains("Cache-Control")) {
            return chain.proceed(request);
        }
        Response response = chain.proceed(request);
        return Utils.getNetworkState(context) == Utils.NETWORK_BAD ?
                modifyResponseCacheControlForBadNetwork(response) :
                modifyResponseCacheControlForNormalAndWeakNetwork(request, response);
    }

    private Response modifyResponseCacheControlForBadNetwork(Response response) {
        return response.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                .removeHeader("Pragma")
                .build();
    }

    private Response modifyResponseCacheControlForNormalAndWeakNetwork(Request request, Response response) {
        return response.newBuilder()
                .header("Cache-Control", request.cacheControl().toString())
                .removeHeader("Pragma")
                .build();
    }
}
