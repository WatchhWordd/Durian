package com.durian.demo.data.net;

import com.durian.demo.data.net.bean.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * @author zhangyb
 * @description
 * @date 2017/10/31
 */

public interface GitDataNetApi {

    String BASE_URL = "https://api.github.com";

    @GET("/user")
    Observable<UserInfo> getUserInfoByAuthorization(@Header("Authorization") String authorization);


}
