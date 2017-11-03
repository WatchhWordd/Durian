package com.example.demo;

import com.example.demo.data.GitDataRepository;
import com.example.demo.domain.GitDataSource;
import com.example.demo.domain.usecase.GetUserInfo;

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
