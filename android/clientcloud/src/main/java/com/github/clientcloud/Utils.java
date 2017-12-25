package com.github.clientcloud;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class Utils {

    public static final int NETWORK_BAD = 0;
    public static final int NETWORK_NORMAL = 1;
    public static final int NETWORK_WEAK = 2;

    private static final List<Integer> weakMobileNetworkSubTypeList = Arrays.asList(
            TelephonyManager.NETWORK_TYPE_GPRS,
            TelephonyManager.NETWORK_TYPE_CDMA,
            TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_1xRTT,
            TelephonyManager.NETWORK_TYPE_IDEN);

    public static int getNetworkState(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return NETWORK_BAD;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE &&
                weakMobileNetworkSubTypeList.contains(networkInfo.getSubtype())) {
            return NETWORK_WEAK;
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            if (networkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_GSM) {
                return NETWORK_WEAK;
            }
        }

        return NETWORK_NORMAL;
    }

    public static String md5(String string) {
        MessageDigest digest;
        StringBuffer buffer = new StringBuffer();
        try {
            digest = MessageDigest.getInstance("md5");

            byte[] result = digest.digest(string.getBytes("utf-8"));
            for (byte b : result) {
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String binaryToHexString(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        String hexStr = "0123456789abcdef";
        for (byte aByte : bytes) {
            hex.append(String.valueOf(hexStr.charAt((aByte & 0xF0) >> 4)));
            hex.append(String.valueOf(hexStr.charAt(aByte & 0x0F)));
        }
        return hex.toString();
    }

    public static String getRequestBodyAsString(Request request) throws IOException {
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return null;
        }
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }

    public static String getResponseBodyAsString(Response response) throws IOException {
        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);
        return source.buffer().clone().readString(Charset.forName("UTF-8"));
    }


}
