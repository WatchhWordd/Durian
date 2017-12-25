package com.durian.demo.domain.usecase;

import com.durian.demo.base.RxUseCase;
import com.durian.demo.data.net.bean.ReposInfo;
import com.durian.demo.domain.GitDataSource;

import java.util.ArrayList;

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
        String type;
        String sort;
        String direction;

        public Request(String userName, String type, String sort, String direction) {
            this.userName = userName;
            this.type = type;
            this.sort = sort;
            this.direction = direction;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
    }
    public static class  Response implements RxUseCase.ResponseValue {

        ArrayList<ReposInfo> reposInfos;

        public Response(ArrayList<ReposInfo> reposInfos) {
            this.reposInfos = reposInfos;
        }

        public ArrayList<ReposInfo> getReposInfos() {
            return reposInfos;
        }

        public void setReposInfos(ArrayList<ReposInfo> reposInfos) {
            this.reposInfos = reposInfos;
        }
    }
}
