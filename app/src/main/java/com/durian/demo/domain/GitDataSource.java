package com.durian.demo.domain;

import com.durian.demo.domain.usecase.GetRepositoryList;
import com.durian.demo.domain.usecase.GetStarsList;
import com.durian.demo.domain.usecase.GetUserFollowers;
import com.durian.demo.domain.usecase.GetUserFollowings;
import com.durian.demo.domain.usecase.GetUserInfo;

import io.reactivex.Observable;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/2
 */

public interface GitDataSource {

    Observable<GetUserInfo.Response> getPostList(String requestValues);

    Observable<GetRepositoryList.Response> getRepositoryList(GetRepositoryList.Request requestValue);

    Observable<GetUserFollowers.Response> getUserFollowers(GetUserFollowers.Request requestValue);

    Observable<GetUserFollowings.Response> getUserFollowings(GetUserFollowings.Request requestValue);

    Observable<GetStarsList.Response> getStarsList(GetStarsList.Request requestValue);


}
