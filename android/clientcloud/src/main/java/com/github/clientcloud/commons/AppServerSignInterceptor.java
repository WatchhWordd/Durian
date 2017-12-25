package com.github.clientcloud.commons;

import com.github.clientcloud.ApiServer;
import com.github.clientcloud.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zhangyb
 * @description
 * @date 2017/9/22
 */

public class AppServerSignInterceptor implements Interceptor {

    private ApiServer apiServer;

    public AppServerSignInterceptor(ApiServer apiServer) {
        this.apiServer = apiServer;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        request = signRequest(request);
        return chain.proceed(request);
    }

    private Request signRequest(Request request) throws IOException{

        String body = Utils.getRequestBodyAsString(request);
        if (body == null) {
            body = "";
        } else {
            body = body.replaceAll("\\s+", "");
        }

        String content = body +
                apiServer.getConfig(ApiServer.Config.APP_ID) +
                apiServer.getConfig(ApiServer.Config.APP_KEY) +
                request.header("timestamp");
        String sign = Utils.md5(content);
        return request.newBuilder().addHeader("sign", sign).build();
    }
}
