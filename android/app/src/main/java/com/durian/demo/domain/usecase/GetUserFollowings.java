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

public class GetUserFollowings extends RxUseCase<GetUserFollowings.Request, GetUserFollowings.Response> {

    private GitDataSource gitDataSource;

    public GetUserFollowings(GitDataSource gitDataSource) {
        this.gitDataSource = gitDataSource;
    }

    @Override
    protected Observable<Response> buildUseCaseObservable(Request requestValues) {
        return gitDataSource.getUserFollowings(requestValues);
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
        private ArrayList<UserInfo> userInfos;

        public Response(ArrayList<UserInfo> userInfo) {
            this.userInfos = userInfo;
        }

        public ArrayList<UserInfo> getUserInfo() {
            return userInfos;
        }
    }
}
