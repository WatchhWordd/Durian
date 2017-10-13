package com.example.githubclientdemo.apiservices;

import com.example.githubclientdemo.entities.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by zhangyb on 2016/12/21.
 */

public interface ApiServiceNeedToken {

    @GET("/user")
    Observable<UserInfo> getUserInfoByAuthorizations(@Header("Authorization") String authorization);
}
