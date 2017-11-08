package com.durian.demo.base.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.durian.demo.R;

/**
 * @author zhangyb
 * @description 工具类
 * @date 2017/11/2
 */

public class ConfigUtil {

    public static final String S_USER_INFO = "user_info_obj";

    public static AlertDialog getAndShowLoadingDialog(Context context, String content){
        AlertDialog loadingDialog = new AlertDialog.Builder(context).create();
        loadingDialog.show();
        loadingDialog.getWindow()
                .setContentView(R.layout.layout_loading_dialog);
        ((TextView) loadingDialog.findViewById(R.id.loading_dlg_echo_text)).setText(content);
        return loadingDialog;
    }
}
