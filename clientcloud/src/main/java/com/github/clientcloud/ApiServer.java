package com.github.clientcloud;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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

    private static final BiFunction<String, String, Boolean> DEFAULT_IS_USE_DEFAULT_CONFIG_FUNCTION =
            (key, value) -> value == null || value.isEmpty();

    public final String getConfig(String key) {
        String value = getLocalConfig(key);
        if (isUseDefaultConfig(key, value)) {
            value = getDefaultConfig(key);
        }
        return value;
    }

    private Boolean isUseDefaultConfig(String key, String value) {
        try {
            return getIsUseDefaultConfigFunction().apply(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final String getLocalConfig(String key) {
        return config.get(key);
    }

    public final void setConfig(String key, String value) {
        config.put(key, value);
    }

    protected BiFunction<String, String, Boolean> getIsUseDefaultConfigFunction() {
        return DEFAULT_IS_USE_DEFAULT_CONFIG_FUNCTION;
    }

    private static Map<String, String> defaultConfig = new HashMap<>();

    private final Map<String, String> config = new HashMap<>();

    public static String getDefaultConfig(String key) {
        return defaultConfig.get(key);
    }

    public static void setDefaultConfig(String key, String value) {
        defaultConfig.put(key, value);
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

    public Retrofit.Builder createRetrofitBuilder(Context context) {
        return Observable.fromIterable(getIniterList())
                .filter(initer -> initer instanceof RetrofitIniter)
                .cast(RetrofitIniter.class)
                .reduce(new Retrofit.Builder(),
                        (builder, initer) -> initer.initialize(builder, this, context))
                .blockingGet();
    }

    public Retrofit.Builder onRetrofitBuilderCreated(Retrofit.Builder builder, Context context){
        return builder;
    };
    public abstract List<String> getBaseUrlList();

    public abstract List<Initer> getIniterList();
}
