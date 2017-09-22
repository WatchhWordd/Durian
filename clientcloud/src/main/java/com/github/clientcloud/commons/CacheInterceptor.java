package com.github.clientcloud.commons;

import android.content.Context;

import com.github.clientcloud.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zhangyb
 * @description 缓存处理
 * @date 2017/9/20
 */

public class CacheInterceptor implements Interceptor {
    private Context context;
    private double weakNetworkMaxAgeRatio;

    public CacheInterceptor(Context context, double weakNetworkMaxAgeRatio) {
        this.context = context;
        this.weakNetworkMaxAgeRatio = weakNetworkMaxAgeRatio;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!request.headers().names().contains("Cache-Control")) {
            return chain.proceed(request);
        }

        int networkState = Utils.getNetworkState(context);

        if (networkState == Utils.NETWORK_BAD) {
            request = modifyRequestCacheControlForBadNetwork(request);

        } else if (networkState == Utils.NETWORK_WEAK) {
            request = modifyRequestCacheControlForWeakNetwork(request);
        }

        return chain.proceed(request);
    }

    private Request modifyRequestCacheControlForBadNetwork(Request request) {
        return request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build();
    }

    private Request modifyRequestCacheControlForWeakNetwork(Request request) {
        CacheControl cacheControl = request.cacheControl();
        int maxAgeSeconds = cacheControl.maxAgeSeconds();
        if (maxAgeSeconds > 0) {
            CacheControl modifiedCacheControl = genCacheControlBuilder(cacheControl)
                    .maxAge((int) (maxAgeSeconds * weakNetworkMaxAgeRatio), TimeUnit.SECONDS)
                    .build();
            request = request.newBuilder()
                    .cacheControl(modifiedCacheControl)
                    .build();
        }
        return request;
    }

    private CacheControl.Builder genCacheControlBuilder(CacheControl cacheControl) {
        CacheControl.Builder builder = new CacheControl.Builder();

        if (cacheControl.noCache()) {
            builder.noCache();
        }

        if (cacheControl.noStore()) {
            builder.noStore();
        }

        builder.maxAge(cacheControl.maxAgeSeconds(), TimeUnit.SECONDS);

        builder.maxStale(cacheControl.maxStaleSeconds(), TimeUnit.SECONDS);

        builder.minFresh(cacheControl.minFreshSeconds(), TimeUnit.SECONDS);

        if (cacheControl.onlyIfCached()) {
            builder.onlyIfCached();
        }

        if (cacheControl.noTransform()) {
            builder.noTransform();
        }

        if (cacheControl.immutable()) {
            builder.immutable();
        }

        return builder;
    }
}
