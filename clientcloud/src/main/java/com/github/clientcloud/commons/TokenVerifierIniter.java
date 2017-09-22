package com.github.clientcloud.commons;

import android.content.Context;

import com.github.clientcloud.ApiServer;
import com.github.clientcloud.OkhttpIniter;

import okhttp3.OkHttpClient;

/**
 * @author zhangyb
 * @description
 * @date 2017/9/19
 */

public class TokenVerifierIniter implements OkhttpIniter {

    private boolean isUpdateDefaultTokenConfig = false;

    public TokenVerifierIniter(boolean isUpdateDefaultTokenConfig) {
        this.isUpdateDefaultTokenConfig = isUpdateDefaultTokenConfig;
    }

    @Override
    public OkHttpClient.Builder initialize(OkHttpClient.Builder builder,
                                           ApiServer apiServer, Context context) {
        if (builder == null) {
            builder = new OkHttpClient.Builder();
        }

        return builder.addInterceptor(new TokenVerifier(apiServer, isUpdateDefaultTokenConfig, context));
    }
}
