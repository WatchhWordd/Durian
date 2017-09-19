package com.github.clientcloud;

import android.content.Context;
import android.util.Pair;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author zhangyb
 * @description
 * @date 2017/9/15
 */

public class GitHubCloud {

    private WeakReference<Context> contextWeakReference;
    private final Map<Class<? extends ApiServer>, ApiServer> apiServerMap = new HashMap<>();

    private final List<String> baseUrlOrderList = new LinkedList<>();
    private final Map<String, Class<? extends ApiServer>> baseUrlMap = new HashMap<>();

    private final Map<Class<? extends ApiServer>, OkHttpClient> okHttpClientMap = new HashMap<>();
    private final Map<Pair<Class<? extends ApiServer>, String>, Retrofit> retrofitMap = new HashMap<>();


    private static class SingletonHolder {
        static AtomicBoolean isInitialized = new AtomicBoolean(false);
        static GitHubCloud instance = new GitHubCloud();
    }

    public static class Initialize {
        private Context context;
        private boolean isEnablePresetApiServers = true;

        public Initialize() {
        }

        public Initialize setContext(Context context) {
            this.context = context;
            return this;
        }

        public Initialize setEnablePresetApiServers(boolean enablePresetApiServers) {
            isEnablePresetApiServers = enablePresetApiServers;
            return this;
        }

        public GitHubCloud init() {
            return SingletonHolder.instance.initialize(this);
        }
    }

    private static void checkNotNullApiServerClass(Class<? extends ApiServer> apiServerClass, String baseUrl) {
        if (apiServerClass == null) {
            throw new IllegalArgumentException("ERROR! Can not find ApiServer by url: " + baseUrl);
        }
    }

    private static void checkNotNullApiServer(ApiServer apiServer, Class<? extends ApiServer> apiServerClass) {
        if (apiServer == null) {
            throw new IllegalArgumentException("ERROR! Can not find ApiServer: " + apiServerClass);
        }
    }

    private GitHubCloud initialize(Initialize initialize) {
        if (!SingletonHolder.isInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("ERROR! gitHub has already been initialized !");
        }

        if (initialize.context == null) {
            throw new IllegalArgumentException("ERROR! The Context is null !");
        }
        this.contextWeakReference = new WeakReference<>(initialize.context);

        if (initialize.isEnablePresetApiServers) {
            registerPresetApiServers();
        }
        return this;
    }

    private void registerPresetApiServers() {
        registerApiServer(new AppServer());
    }

    /**
     * 将要访问的地址加在集合中
     *
     * @param apiServer 当前访问地址对象
     */
    private void registerApiServer(ApiServer apiServer) {
        Class<? extends ApiServer> apiServerClass = apiServer.getClass();
        if (apiServerMap.containsKey(apiServerClass)) {
            throw new IllegalArgumentException("ERROR! Already existed ApiServer: " + apiServerClass);
        }
        apiServerMap.put(apiServerClass, apiServer);
        List<String> baseUrlList = apiServer.getBaseUrlList();
        if (baseUrlList == null || baseUrlList.isEmpty()) {
            return;
        }

        for (String baseUrl : baseUrlList) {
            if (HttpUrl.parse(baseUrl) == null) {
                throw new IllegalArgumentException("ERROR! Illegal url: " + baseUrl);
            }

            if (baseUrlOrderList.contains(baseUrl)) {
                throw new IllegalArgumentException("ERROR! Already existed base url: " + baseUrl);
            }

            baseUrlMap.put(baseUrl, apiServerClass);
            baseUrlOrderList.add(baseUrl);
        }

        Collections.sort(baseUrlOrderList, (left, right) -> right.length() - left.length());
    }

    /**
     * 获取服务器配置类实例类对象
     *
     * @param url 服务接口URL
     * @return 服务器配置类实例类对象
     */
    public Class<? extends ApiServer> getApiServerClass(String url) {
        for (String registeredBaseUrl : baseUrlOrderList) {
            if (url.startsWith(registeredBaseUrl)) {
                return baseUrlMap.get(registeredBaseUrl);
            }
        }
        return null;
    }

    // ------------------------------------------------------------------------

    /**
     * 获取OkHttp客户端实例
     * OkHttp实例使用服务器配置类的class对象作为标识，并且会缓存以备重用
     * 首先使用url查询到class对象，然后使用class对象查询OkHttp实例
     * 如果OkHttp实例已经存在，则将其直接返回以重用；反之，将创建新的OkHttp实例，并将其加入缓存中
     *
     * @param url 服务接口URL
     * @return OkHttp客户端实例
     */
    public OkHttpClient getOkHttpClient(String url) {
        Class<? extends ApiServer> apiServerClass = getApiServerClass(url);
        checkNotNullApiServerClass(apiServerClass, url);
        return getOkHttpClient(apiServerClass);
    }

    private OkHttpClient getOkHttpClient(Class<? extends ApiServer> apiServerClass) {
        OkHttpClient client = okHttpClientMap.get(apiServerClass);

        if (client == null) {
            client = createOkHttpClient(apiServerClass);
        }

        if (client != null) {
            okHttpClientMap.put(apiServerClass, client);
        }

        return client;
    }

    private OkHttpClient createOkHttpClient(Class<? extends ApiServer> apiServerClass) {
        ApiServer apiServer = getApiServer(apiServerClass);
        checkNotNullApiServer(apiServer, apiServerClass);

        Context context = contextWeakReference.get();
        OkHttpClient.Builder builder = apiServer.createOkHttpBuilder(context);
        if (builder == null) {
            return null;
        }

        builder = apiServer.onOkHttpBuilderCreated(builder, context);
        return builder.build();
    }

    /* 创建Retrofit接口对象
     *
     * @param baseUrl           接口服务器的基础URL
     * @param apiInterfaceClass Retrofit接口类的class对象
     * @return Retrofit接口对象
     */
    public <T> T createRetrofitApi(String baseUrl, Class<T> apiInterfaceClass) {
        Class<? extends ApiServer> apiServerClass = getApiServerClass(baseUrl);
        checkNotNullApiServerClass(apiServerClass, baseUrl);
        return createRetrofitApi(apiServerClass, baseUrl, apiInterfaceClass);
    }

    private <T> T createRetrofitApi(Class<? extends ApiServer> apiServerClass, String baseUrl,
                                    Class<T> apiInterfaceClass) {
        Retrofit retrofit = getRetrofit(apiServerClass, baseUrl);
        if (retrofit == null) {
            return null;
        }

        return retrofit.create(apiInterfaceClass);
    }

    /**
     * 获取Retrofit实例对象
     * Retrofit实例使用apiServerClass和baseUrl联合作为标识，并且会缓存以备重用
     * 使用上述标识查询Retrofit实例对象
     * 如果已经存在，则将其直接返回，反之创建新的Retrofit实例对象，并加入缓存
     *
     * @param apiServerClass 服务器配置类的class对象
     * @param baseUrl        接口服务器的基础URL
     * @return Retrofit实例对象
     */
    public Retrofit getRetrofit(Class<? extends ApiServer> apiServerClass, String baseUrl) {
        Pair<Class<? extends ApiServer>, String> pair = Pair.create(apiServerClass, baseUrl);
        Retrofit retrofit = retrofitMap.get(pair);

        if (retrofit == null) {
            retrofit = createRetrofit(apiServerClass, baseUrl);
        }

        if (retrofit != null) {
            retrofitMap.put(pair, retrofit);
        }

        return retrofit;
    }

    private Retrofit createRetrofit(Class<? extends ApiServer> apiServerClass, String baseUrl) {
        ApiServer apiServer = getApiServer(apiServerClass);
        checkNotNullApiServer(apiServer, apiServerClass);
        Context context = contextWeakReference.get();
        Retrofit.Builder builder = apiServer.createRetrofitBuilder(context);
        if (builder == null) {
            return null;
        }

        OkHttpClient okHttpClient = getOkHttpClient(apiServerClass);
        if (okHttpClient != null) {
            builder.callFactory(okHttpClient);
        }

        if (baseUrl != null) {
            builder.baseUrl(baseUrl);
        }

        builder = apiServer.onRetrofitBuilderCreated(builder, context);
        return builder.build();
    }

    /**
     * 获取服务器配置类实例
     *
     * @param apiServerClass 服务器配置类的class对象
     * @return 服务器配置类实例
     */
    public ApiServer getApiServer(Class<? extends ApiServer> apiServerClass) {
        return apiServerMap.get(apiServerClass);
    }

}
