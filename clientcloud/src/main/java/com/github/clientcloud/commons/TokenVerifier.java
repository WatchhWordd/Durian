package com.github.clientcloud.commons;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.github.clientcloud.ApiServer;
import com.github.clientcloud.Utils;
import com.github.clientcloud.commons.CommonResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author zhangyb
 * @description
 * @date 2017/9/21
 */

class TokenVerifier implements Interceptor {

    public static final String INTENT_ACTION_NEW_TOKEN =
            "com.github.clientcloud.commons.TokenVerifier.NEW_TOKEN";
    public static final String INTENT_ACTION_INVALID_TOKEN =
            "com.github.clientcloud.commons.TokenVerifier.INVALID_TOKEN";
    public static final String INTENT_KEY_ACCESS_TOKEN = "accessToken";
    public static final String INTENT_KEY_API_SERVER = "apiServer";

    private static final String ACCESS_TOKEN = "accessToken";

    private final ApiServer apiServer;
    private final Context context;
    private final boolean isUpdateDefaultConfig;

    private static List<String> invalidCodeList = Arrays.asList(
            "21016", "B00007-21016", "21018", "B00007-21018", "21019", "B00007-21019");

    public TokenVerifier(ApiServer apiServer,
                         boolean isUpdateDefaultTokenConfig, Context context) {
        this.apiServer = apiServer;
        this.context = context;
        this.isUpdateDefaultConfig = isUpdateDefaultTokenConfig;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String bodyString = Utils.getResponseBodyAsString(response);

        try {
            CommonResponse commonResponse = new Gson().fromJson(bodyString, CommonResponse.class);
            String retCode = commonResponse.getRetCode();
            if (invalidCodeList.contains(retCode)) {
                Intent intent = new Intent(INTENT_ACTION_INVALID_TOKEN)
                        .putExtra(INTENT_KEY_API_SERVER, apiServer.getClass().getName());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        } catch (Exception ignored) {
            // nothing
        }

        String accessToken = response.header(ACCESS_TOKEN);
        String lastAccessToken = apiServer.getConfig(ApiServer.Config.ACCESS_TOKEN);

        if (accessToken != null && !accessToken.equals(lastAccessToken)) {

            apiServer.setConfig(ApiServer.Config.ACCESS_TOKEN, accessToken);

            if (isUpdateDefaultConfig) {
                ApiServer.setDefaultConfig(ApiServer.Config.ACCESS_TOKEN, accessToken);
            }

            if (context == null) {
                return response;
            }

            Intent intent = new Intent(INTENT_ACTION_NEW_TOKEN)
                    .putExtra(INTENT_KEY_ACCESS_TOKEN, accessToken)
                    .putExtra(INTENT_KEY_API_SERVER, apiServer.getClass().getName());
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
        return response;
    }
}
