package com.github.clientcloud;

import com.github.clientcloud.appserver.AppServerHeadersIniter;
import com.github.clientcloud.appserver.AppServerSignIniter;
import com.github.clientcloud.appserver.CacheIniter;
import com.github.clientcloud.appserver.GsonConverterIniter;
import com.github.clientcloud.appserver.RxJava2CallAdapterIniter;
import com.github.clientcloud.appserver.ScalarsConverterIniter;
import com.github.clientcloud.appserver.TimeoutIniter;
import com.github.clientcloud.appserver.TokenVerifierIniter;
import com.github.clientcloud.appserver.UnsafeSslIniter;
import com.github.clientcloud.appserver.ValidateEagerlyIniter;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangyb
 * @description
 * @date 2017/9/15
 */

public class AppServer extends ApiServer {
    @Override
    public List<String> getBaseUrlList() {
        return Arrays.asList(
                "https://uhome.golab.net");
    }

    @Override
    public List<Initer> getIniterList() {
        return Arrays.asList(
                new AppServerHeadersIniter(),
                new AppServerSignIniter(),
                new CacheIniter(),
                new UnsafeSslIniter(),
                new TokenVerifierIniter(true),
                new ScalarsConverterIniter(),
                new GsonConverterIniter(),
                new RxJava2CallAdapterIniter(),
                new ValidateEagerlyIniter(),
                new TimeoutIniter());
    }
}
