package com.example.githubclientdemo.params;

/**
 * Created by zhangyb on 2016/12/21.
 */

public class UserLoginParams {
    public String userName;
    public String userPassword;

    public UserLoginParams(String userPassword, String userName) {
        this.userPassword = userPassword;
        this.userName = userName;
    }
}
