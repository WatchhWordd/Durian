package com.durian.demo.base.net;

/**
 * @author zhangyb
 * @description
 * @date 2018/5/16
 */
public class NetState {

    private String ip;
    private NetType netType;
    private String ssid;
    private String wifiName;
    private String getWay;

    public boolean isConnection(){
        return netType != null && netType != NetType.NONE;
    }
    public String getGetWay() {
        return getWay == null ? "" : getWay;
    }

    public void setGetWay(String getWay) {
        this.getWay = getWay;
    }

    public String getIp() {
        return ip == null ? "" : ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public String getSsid() {
        return ssid == null ? "" : ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getWifiName() {
        return wifiName == null ? "" : wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }
}
