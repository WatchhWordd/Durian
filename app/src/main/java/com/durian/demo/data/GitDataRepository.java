package com.durian.demo.data;

import com.durian.demo.data.net.GitDataNetApi;
import com.durian.demo.domain.GitDataSource;
import com.durian.demo.domain.usecase.GetRepositoryList;
import com.durian.demo.domain.usecase.GetStarsList;
import com.durian.demo.domain.usecase.GetUserFollowers;
import com.durian.demo.domain.usecase.GetUserFollowings;
import com.durian.demo.domain.usecase.GetUserInfo;
import com.github.clientcloud.AppServerConfig;
import com.github.clientcloud.GitHubCloud;

import io.reactivex.Observable;

/**
 * @author zhangyb
 * @description Repository
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
                .map(response -> new GetUserInfo.Response(response));
    }

    @Override
    public Observable<GetRepositoryList.Response> getRepositoryList(GetRepositoryList.Request requestValue) {
        return gitDataNetApi.getRepositoryList(requestValue.getUserName(), requestValue.getParams())
                .map(response -> new GetRepositoryList.Response(response));
    }

    @Override
    public Observable<GetUserFollowers.Response> getUserFollowers(GetUserFollowers.Request requestValue) {
        return gitDataNetApi.getUserFollowers(requestValue.getUserName())
                .map(response -> new GetUserFollowers.Response(response));
    }

    @Override
    public Observable<GetUserFollowings.Response> getUserFollowings(GetUserFollowings.Request requestValue) {
        return gitDataNetApi.getUserFollowings(requestValue.getUserName())
                .map(response -> new GetUserFollowings.Response(response));
    }

    @Override
    public Observable<GetStarsList.Response> getStarsList(GetStarsList.Request requestValue) {
        return gitDataNetApi.getUserStarred(requestValue.getUserName())
                .map(response ->new GetStarsList.Response(response));
    }
}
