package com.durian.demo;

import com.durian.demo.data.GitDataRepository;
import com.durian.demo.domain.GitDataSource;
import com.durian.demo.domain.usecase.GetUserInfo;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/2
 */

public class GitDataInjection {

    public static GitDataSource provideGitDataSource() {
        return new GitDataRepository();
    }

    public static GetUserInfo provideGgetUserInfo(){
        return new GetUserInfo(provideGitDataSource());
    }
}
