package com.example.githubclientdemo;

/**
 * Created by zhangyb on 2016/12/20.
 */

public interface BasePersistence<T> {
    void saveData(T data);

    void deleteData(T data);

    T retrieveData();
}
