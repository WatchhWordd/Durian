package com.github.clientcloud;

import com.github.clientcloud.appserver.AppServerHeadersIniter;
import com.github.clientcloud.commons.AppServerSignIniter;
import com.github.clientcloud.commons.CacheIniter;
import com.github.clientcloud.commons.GsonConverterIniter;
import com.github.clientcloud.commons.LoggerIniter;
import com.github.clientcloud.commons.RxJava2CallAdapterIniter;
import com.github.clientcloud.commons.ScalarsConverterIniter;
import com.github.clientcloud.commons.TimeoutIniter;
import com.github.clientcloud.commons.TokenVerifierIniter;
import com.github.clientcloud.commons.UnsafeSslIniter;
import com.github.clientcloud.commons.ValidateEagerlyIniter;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangyb
 * @description
 * @date 2017/9/15
 */

public class AppServerConfig extends ApiServer {
    @Override
    public List<String> getBaseUrlList() {
        return Arrays.asList(
                "https://api.github.com");
    }

    @Override
    public List<Initer> getIniterList() {
        return Arrays.asList(
                new AppServerHeadersIniter(),
                new AppServerSignIniter(),
                new CacheIniter(),
                new LoggerIniter(),
                new UnsafeSslIniter(),
                new TokenVerifierIniter(true),
                new ScalarsConverterIniter(),
                new GsonConverterIniter(),
                new RxJava2CallAdapterIniter(),
                new ValidateEagerlyIniter(),
                new TimeoutIniter());
    }
}
