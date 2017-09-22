package com.github.clientcloud.commons;

import android.content.Context;

import com.github.clientcloud.ApiServer;
import com.github.clientcloud.OkhttpIniter;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * @author zhangyb
 * @description
 * @date 2017/9/19
 */

public class UnsafeSslIniter implements OkhttpIniter {
    @Override
    public OkHttpClient.Builder initialize(OkHttpClient.Builder builder,
                                           ApiServer apiServer, Context context) {
        if (builder == null) {
            builder = new OkHttpClient.Builder();
        }

        return builder.sslSocketFactory(getUnsafeSslSocketFactory(), getUnsafeX509TrustManager())
                .hostnameVerifier(getUnsafeHostnameVerifier());
    }

    private SSLSocketFactory getUnsafeSslSocketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                getUnsafeX509TrustManager()
        };

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return null;
    }

    private X509TrustManager getUnsafeX509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    private HostnameVerifier getUnsafeHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession sslSession) {
                return true;
            }
        };
    }
}
