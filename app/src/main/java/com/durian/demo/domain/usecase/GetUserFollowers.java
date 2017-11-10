package com.durian.demo.domain.usecase;

import com.durian.demo.base.RxUseCase;
import com.durian.demo.data.net.bean.UserInfo;
import com.durian.demo.domain.GitDataSource;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * @author zhangyb
 * @description useCase
 * @date 2017/11/10
 */

public class GetUserFollowers extends RxUseCase<GetUserFollowers.Request, GetUserFollowers.Response> {
    private GitDataSource gitDataSource;

    public GetUserFollowers(GitDataSource gitDataSource) {
        this.gitDataSource = gitDataSource;
    }

    @Override
    protected Observable<Response> buildUseCaseObservable(Request requestValues) {
        return gitDataSource.getUserFollowers(requestValues);
    }

    public static class Request implements RxUseCase.RequestValues {
        String userName;

        public Request(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class Response implements RxUseCase.ResponseValue {

        ArrayList<UserInfo> userInfos;

        public Response(ArrayList<UserInfo> userInfos) {
            this.userInfos = userInfos;
        }

        public ArrayList<UserInfo> getUserInfos() {
            return userInfos;
        }

        public void setUserInfos(ArrayList<UserInfo> userInfos) {
            this.userInfos = userInfos;
        }
    }


}
