package com.durian.demo.data.net.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/15
 */

public class LoadParam implements Serializable{

    private int type =0 ;//0:reposList

    private ArrayList<UserInfo> userInfos;

    private ArrayList<ReposInfo> reposInfos;

    public LoadParam() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(ArrayList<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public ArrayList<ReposInfo> getReposInfos() {
        return reposInfos;
    }

    public void setReposInfos(ArrayList<ReposInfo> reposInfos) {
        this.reposInfos = reposInfos;
    }
}
