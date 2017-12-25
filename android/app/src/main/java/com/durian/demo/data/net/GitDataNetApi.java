package com.durian.demo.data.net;

import com.durian.demo.data.net.bean.ReposInfo;
import com.durian.demo.data.net.bean.UserInfo;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author zhangyb
 * @description
 * @date 2017/10/31
 */

public interface GitDataNetApi {

    String BASE_URL = "https://api.github.com";

    //获取用户信息
    @GET("/user")
    Observable<UserInfo> getUserInfoByAuthorization(@Header("Authorization") String authorization);

    //获取Repository列表
    @GET("/users/{username}/repos")
    Observable<ArrayList<ReposInfo>> getRepositoryList(@Path("username") String userName,
                                                       @Query("type") String type,
                                                       @Query("sort") String sort,
                                                       @Query("direction") String direction);

    //获取 following 列表
    @GET("/users/{username}/following")
    Observable<ArrayList<UserInfo>> getUserFollowings(@Path("username") String userName);

    //获取follower列表
    @GET("/users/{username}/followers")
    Observable<ArrayList<UserInfo>> getUserFollowers(@Path("username") String userName);

    @GET("/users/{username}/starred")
    Observable<ArrayList<ReposInfo>> getUserStarred(@Path("username") String userName);
}
