package com.github.clientcloud.commons;

import android.content.Context;

import com.github.clientcloud.ApiServer;
import com.github.clientcloud.OkhttpIniter;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;



/**
 * @author zhangyb
 * @description 网络缓存初始化
 * @date 2017/9/19
 */

public class CacheIniter implements OkhttpIniter {
    private static final double DEFAULT_WEAK_NETWORK_MAX_AGE_RATIO = 2.0;
    private static final int DEFAULT_MAX_CACHE_SIZE = 100 * 1024 * 1024;

    private double weakNetworkMaxAgeRatio;
    private long maxCacheSize;
    private File cacheFolder;

    public CacheIniter() {
        this(DEFAULT_WEAK_NETWORK_MAX_AGE_RATIO, DEFAULT_MAX_CACHE_SIZE, null);
    }

    public CacheIniter(double weakNetworkMaxAgeRatio, long maxCacheSize, File cacheFolder) {
        this.weakNetworkMaxAgeRatio = weakNetworkMaxAgeRatio;
        this.maxCacheSize = maxCacheSize;
        this.cacheFolder = cacheFolder;
    }

    @Override
    public OkHttpClient.Builder initialize(OkHttpClient.Builder target,
                                           ApiServer apiServer, Context context) {

        if (target == null) {
            target = new OkHttpClient.Builder();
        }

        if (cacheFolder == null) {
            cacheFolder = new File(context.getCacheDir(),
                    "clientCloud" + apiServer.getClass().getSimpleName());
        }

        return target.cache(new Cache(cacheFolder, maxCacheSize))
                .addInterceptor(new CacheInterceptor(context, weakNetworkMaxAgeRatio))
                .addNetworkInterceptor(new CacheNetworkInterceptor(context));
    }
}
