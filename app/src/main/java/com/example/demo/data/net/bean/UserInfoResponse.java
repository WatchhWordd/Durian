package com.example.demo.data.net.bean;

import com.github.clientcloud.commons.CommonResponse;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/3
 */

public class UserInfoResponse extends CommonResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        UserInfo userInfo;

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }
    }
}
