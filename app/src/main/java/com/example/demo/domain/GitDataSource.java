package com.example.demo.domain;

import com.example.demo.domain.usecase.GetUserInfo;

import io.reactivex.Observable;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/2
 */

public interface GitDataSource {

    Observable<GetUserInfo.Response> getPostList(String requestValues);
}
