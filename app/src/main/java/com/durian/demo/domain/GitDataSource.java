package com.durian.demo.domain;

import com.durian.demo.domain.usecase.GetUserInfo;

import io.reactivex.Observable;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/2
 */

public interface GitDataSource {

    Observable<GetUserInfo.Response> getPostList(String requestValues);
}
