package com.soyoung.component_base.network;

import android.content.Context;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.util.LogUtils;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

//import lib.http.ChuckInterceptor;
import okhttp3.OkHttpClient;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/7/4
 * description   : OkHttpClient 生产类
 */

public class OkHttpClientFactory {
    private static final String SOYOUNG_KEY =
            "-----BEGIN CERTIFICATE-----\n" +
                    "MIIDrzCCApegAwIBAgIQCDvgVpBCRrGhdWrJWZHHSjANBgkqhkiG9w0BAQUFADBh\n" +
                    "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3\n" +
                    "d3cuZGlnaWNlcnQuY29tMSAwHgYDVQQDExdEaWdpQ2VydCBHbG9iYWwgUm9vdCBD\n" +
                    "QTAeFw0wNjExMTAwMDAwMDBaFw0zMTExMTAwMDAwMDBaMGExCzAJBgNVBAYTAlVT\n" +
                    "MRUwEwYDVQQKEwxEaWdpQ2VydCBJbmMxGTAXBgNVBAsTEHd3dy5kaWdpY2VydC5j\n" +
                    "b20xIDAeBgNVBAMTF0RpZ2lDZXJ0IEdsb2JhbCBSb290IENBMIIBIjANBgkqhkiG\n" +
                    "9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4jvhEXLeqKTTo1eqUKKPC3eQyaKl7hLOllsB\n" +
                    "CSDMAZOnTjC3U/dDxGkAV53ijSLdhwZAAIEJzs4bg7/fzTtxRuLWZscFs3YnFo97\n" +
                    "nh6Vfe63SKMI2tavegw5BmV/Sl0fvBf4q77uKNd0f3p4mVmFaG5cIzJLv07A6Fpt\n" +
                    "43C/dxC//AH2hdmoRBBYMql1GNXRor5H4idq9Joz+EkIYIvUX7Q6hL+hqkpMfT7P\n" +
                    "T19sdl6gSzeRntwi5m3OFBqOasv+zbMUZBfHWymeMr/y7vrTC0LUq7dBMtoM1O/4\n" +
                    "gdW7jVg/tRvoSSiicNoxBN33shbyTApOB6jtSj1etX+jkMOvJwIDAQABo2MwYTAO\n" +
                    "BgNVHQ8BAf8EBAMCAYYwDwYDVR0TAQH/BAUwAwEB/zAdBgNVHQ4EFgQUA95QNVbR\n" +
                    "TLtm8KPiGxvDl7I90VUwHwYDVR0jBBgwFoAUA95QNVbRTLtm8KPiGxvDl7I90VUw\n" +
                    "DQYJKoZIhvcNAQEFBQADggEBAMucN6pIExIK+t1EnE9SsPTfrgT1eXkIoyQY/Esr\n" +
                    "hMAtudXH/vTBH1jLuG2cenTnmCmrEbXjcKChzUyImZOMkXDiqw8cvpOp/2PV5Adg\n" +
                    "06O/nVsJ8dWO41P0jmP6P6fbtGbfYmbW0W5BjfIttep3Sp+dWOIrWcBAI+0tKIJF\n" +
                    "PnlUkiaY4IBIqDfv8NZ5YBberOgOzW6sRBc4L0na4UU+Krk2U886UAb3LujEV0ls\n" +
                    "YSEY1QSteDwsOoBrp+uvFRTp2InBuThs4pFsiv9kuXclVzDAGySj4dzp30d8tbQk\n" +
                    "CAUw7C29C79Fv1C5qfPrmAESrciIxpg0X40KPMbp1ZWVbd4=\n" +
                    "-----END CERTIFICATE-----";

    private final X509TrustManager trustAllCert;

    public static OkHttpClientFactory getInstance() {
        return OkHttpClientFactoryLoader.INSTANCE;
    }

    private static class OkHttpClientFactoryLoader {
        private static final OkHttpClientFactory INSTANCE = new OkHttpClientFactory();
    }

    private OkHttpClientFactory() {
        // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
        trustAllCert = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        };
    }


    /**
     * 新版网络请求 初始化
     */
    public OkHttpClient initSSLOkHttpClient(Context context,final boolean checkSSL) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(10 * 1000, TimeUnit.MILLISECONDS);
        try {
            if (checkSSL) {
                InputStream certificate = new ByteArrayInputStream(SOYOUNG_KEY.getBytes("UTF-8"));
                X509TrustManager x509TrustManager = trustManagerForCertificates(certificate);//以流的方式读入证书
                if (null != x509TrustManager) {
                    LogUtils.e("initOkHttpClient(OkHttpClientFactory.java:539)" + "配置证书");
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
                    SSLSocketFactory socketFactory = sslContext.getSocketFactory();
                    builder.sslSocketFactory(socketFactory, x509TrustManager);
                } else {
                    SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
                    builder.sslSocketFactory(sslSocketFactory, trustAllCert);//忽略证书
                }

            } else {
                SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
                builder.sslSocketFactory(sslSocketFactory, trustAllCert);//忽略证书
            }
        } catch (Exception e) {
            e.printStackTrace();
            SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
            builder.sslSocketFactory(sslSocketFactory, trustAllCert);//忽略证书
        }
        //if (Constant.IS_DEBUG) {//网络日志
        //    builder.addInterceptor(new ChuckInterceptor());
        //}
        builder.proxy(Proxy.NO_PROXY)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return hostname.equals("api.soyoung.com");
                    }
                });
        return builder.build();
    }

    /**
     * 新版网络请求 初始化 上传图片
     * 关于上传需要特殊处理 不能添加网络日志拦截器
     */
    public OkHttpClient initOkHttpUploadClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .writeTimeout(60000, TimeUnit.MILLISECONDS);
        final boolean checkSSL = AppPreferencesHelper.getBoolean(AppPreferencesHelper.CHECK_SSL, false);
        try {
            if (checkSSL) {
                InputStream certificate = new ByteArrayInputStream(SOYOUNG_KEY.getBytes("UTF-8"));
                X509TrustManager x509TrustManager = trustManagerForCertificates(certificate);//以流的方式读入证书
                if (null != x509TrustManager) {
                    LogUtils.e("initOkHttpClient(OkHttpClientFactory.java:539)" + "配置证书");
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
                    SSLSocketFactory socketFactory = sslContext.getSocketFactory();
                    builder.sslSocketFactory(socketFactory, x509TrustManager);
                } else {
                    SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
                    builder.sslSocketFactory(sslSocketFactory, trustAllCert);//忽略证书
                }
            } else {
                SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
                builder.sslSocketFactory(sslSocketFactory, trustAllCert);//忽略证书
            }
        } catch (Exception e) {
            e.printStackTrace();
            SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
            builder.sslSocketFactory(sslSocketFactory, trustAllCert);//忽略证书
        }
        builder.proxy(Proxy.NO_PROXY)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return hostname.equals("api.soyoung.com");
                    }
                });
        return builder.build();
    }

    /**
     * 适用于 第三方网络请求 不能添加ssl验证
     */
    public OkHttpClient initDefaultOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(10 * 1000, TimeUnit.MILLISECONDS);
        try {
            SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
            builder.sslSocketFactory(sslSocketFactory, trustAllCert);//忽略证书
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Constant.IS_DEBUG) {//网络日志
            //builder.addInterceptor(new ChuckInterceptor());
        } else {
            builder.addInterceptor(new HttpLoggingInterceptor());
        }
        return builder.build();
    }


    /**
     * 单项认证
     */
    private X509TrustManager trustManagerForCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias,
                        certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
