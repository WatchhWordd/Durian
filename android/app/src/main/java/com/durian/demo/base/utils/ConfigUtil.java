package com.durian.demo.base.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.durian.demo.R;

/**
 * @author zhangyb
 * @description 工具类
 * @date 2017/11/2
 */

public class ConfigUtil {

    public static final String S_USER_INFO = "user_info_obj";
    public static final int USER_SAVE_DATE = 30 * 24 * 3600; //缓存一个月

    //acache
    public static final String S_FOLLOWERS = "followers_list";
    public static final String S_FOLLOWINGS = "following_list";
    public static final String S_STARRED = "starred_list";
    public static final String S_REPOES = "repo_list";

    public static AlertDialog getAndShowLoadingDialog(Context context, String content) {
        AlertDialog loadingDialog = new AlertDialog.Builder(context).create();
        loadingDialog.show();
        loadingDialog.getWindow()
                .setContentView(R.layout.layout_loading_dialog);
        ImageView imageView=loadingDialog.findViewById(R.id.loading_dlg_image);
        Glide.with(context).load(context.getResources().getDrawable(R.drawable.ic_github_loading))
                .asGif().into(imageView);
        ((TextView) loadingDialog.findViewById(R.id.loading_dlg_echo_text)).setText(content);
        return loadingDialog;
    }
}
