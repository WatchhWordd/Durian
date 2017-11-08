package com.durian.demo.presentation.overview;


import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/8
 */

public class OverViewFragment extends Fragment {

    private static final String ARG_USERNAME = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static OverViewFragment newInstance(String param1, String param2) {
        OverViewFragment fragment = new OverViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
}
