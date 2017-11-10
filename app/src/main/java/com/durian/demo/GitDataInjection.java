package com.durian.demo;

import com.durian.demo.data.GitDataRepository;
import com.durian.demo.domain.GitDataSource;
import com.durian.demo.domain.usecase.GetRepositoryList;
import com.durian.demo.domain.usecase.GetStarsList;
import com.durian.demo.domain.usecase.GetUserFollowers;
import com.durian.demo.domain.usecase.GetUserFollowings;
import com.durian.demo.domain.usecase.GetUserInfo;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/2
 */

public class GitDataInjection {

    public static GitDataSource provideGitDataSource() {
        return new GitDataRepository();
    }

    public static GetUserInfo provideGetUserInfo() {
        return new GetUserInfo(provideGitDataSource());
    }

    public static GetRepositoryList provideGetReositoryList() {
        return new GetRepositoryList(provideGitDataSource());
    }

    public static GetUserFollowers provideGetUserFollowers() {
        return new GetUserFollowers(provideGitDataSource());
    }

    public static GetUserFollowings provideGetUserFollowing() {
        return new GetUserFollowings(provideGitDataSource());
    }

    public static GetStarsList provideGetStarsList(){
        return  new GetStarsList(provideGitDataSource());
    }
}
