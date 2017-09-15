package com.github.clientcloud;

import android.content.Context;

/**
 * @author zhangyb
 * @description 初始化接口
 * @date 2017/9/15
 */

public interface Initer<T> {
    T initialize(T target, ApiServer apiServer, Context context);
}
