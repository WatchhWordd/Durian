package com.durian.demo.domain.usecase;

import com.durian.demo.base.RxUseCase;
import com.durian.demo.data.net.bean.ReposInfo;
import com.durian.demo.domain.GitDataSource;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author zhangyb
 * @description useCase
 * @date 2017/11/10
 */

public class GetRepositoryList extends RxUseCase<GetRepositoryList.Request,GetRepositoryList.Response>{


    private GitDataSource gitDataSource;

    public GetRepositoryList(GitDataSource gitDataSource) {
        this.gitDataSource = gitDataSource;
    }

    @Override
    protected Observable<Response> buildUseCaseObservable(Request requestValues) {
        return gitDataSource.getRepositoryList(requestValues);
    }

    public static class  Request implements RxUseCase.RequestValues{
        String userName;
        Map<String, Object> params;

        public Request(String userName, Map<String, Object> params) {
            this.userName = userName;
            this.params = params;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }
    }
    public static class  Response implements RxUseCase.ResponseValue {

        ArrayList<ReposInfo> reposInfos;

        public Response(ArrayList<ReposInfo> reposInfos) {
            this.reposInfos = reposInfos;
        }
    }
}
