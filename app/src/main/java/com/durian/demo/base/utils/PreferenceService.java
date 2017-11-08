package com.durian.demo.base.utils;

import com.baoyz.treasure.Default;
import com.baoyz.treasure.Preferences;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/6
 */

@Preferences(edit = Preferences.Edit.COMMIT)
public interface PreferenceService {

    @Default("")
    String getUsername();

    void setUsername(String username);

    @Default("")
    String getUserLoginAccount();

    void setLoginAccount(String account);

    @Default("")
    String getBasicCredential();

    void setBasicCredential(String basicCredential);
}
