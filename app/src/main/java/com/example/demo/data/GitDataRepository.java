package com.example.demo.data;

import com.example.demo.data.net.GitDataNetApi;
import com.example.demo.data.net.bean.UserInfo;
import com.example.demo.domain.GitDataSource;
import com.example.demo.domain.usecase.GetUserInfo;
import com.github.clientcloud.AppServerConfig;
import com.github.clientcloud.GitHubCloud;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @author zhangyb
 * @description
 * @date 2017/10/31
 */

public class GitDataRepository implements GitDataSource {

    private GitDataNetApi gitDataNetApi;

    public GitDataRepository() {
        this.gitDataNetApi = GitHubCloud.getInstance()
                .createRetrofitApi(AppServerConfig.class, gitDataNetApi.BASE_URL, GitDataNetApi.class);
    }

    @Override
    public Observable<GetUserInfo.Response> getPostList(String requestValues) {
        return gitDataNetApi.getUserInfoByAuthorization(requestValues)
                .map(new Function<UserInfo, GetUserInfo.Response>() {
                         @Override
                         public GetUserInfo.Response apply(UserInfo response) throws Exception {
                             return new GetUserInfo.Response(response);
                         }
                     }
                );
    }
}
