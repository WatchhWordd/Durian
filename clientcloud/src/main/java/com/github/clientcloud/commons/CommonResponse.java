package com.github.clientcloud.commons;

/**
 * @author zhangyb
 * @description
 * @date 2017/9/21
 */

public class CommonResponse {

    public static final String SUCCESS_CODE = "00000";
    private String retCode;
    private String retInfo;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(retCode);
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "retCode='" + retCode + '\'' +
                ", retInfo='" + retInfo + '\'' +
                '}';
    }
}
