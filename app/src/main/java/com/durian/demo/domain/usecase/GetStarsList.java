package com.durian.demo.domain.usecase;

import com.durian.demo.base.RxUseCase;
import com.durian.demo.data.net.bean.ReposInfo;
import com.durian.demo.domain.GitDataSource;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/10
 */

public class GetStarsList extends RxUseCase<GetStarsList.Request,GetStarsList.Response>{
    private GitDataSource gitDataSource;

    public GetStarsList(GitDataSource gitDataSource) {
        this.gitDataSource = gitDataSource;
    }

    @Override
    protected Observable<Response> buildUseCaseObservable(Request requestValues) {
        return gitDataSource.getStarsList(requestValues);
    }

    public static class  Request implements RxUseCase.RequestValues{
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
    public static class  Response implements RxUseCase.ResponseValue {

        ArrayList<ReposInfo> reposInfos;

        public Response(ArrayList<ReposInfo> reposInfos) {
            this.reposInfos = reposInfos;
        }
    }
}
