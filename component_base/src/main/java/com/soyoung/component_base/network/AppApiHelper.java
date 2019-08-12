package com.soyoung.component_base.network;

import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.soyoung.component_base.util.AppUtils;
import com.soyoung.component_base.util.Utils;

import java.util.Map;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/2/27
 * description   : 整个项目网络请求父类
 */

public class AppApiHelper {
    protected static final String PAGE_SIZE = "20";

    public AppApiHelper() {
    }
    public static AppApiHelper getInstance() {
        return AppApiHelperLoader.INSTANCE;
    }

    private static class AppApiHelperLoader {
        private static final AppApiHelper INSTANCE = new AppApiHelper();
    }

    /**
     * 添加公参数
     * @param params 传递参数
     * @return 传递参数+公共参数
     */
    public JSONObject  getCommonParasm(HashMap<String, String> params) {
        return ApiHeader.getCommonParasm(params);
    }
    /**
     * 添加公参数
     * @return 公共参数
     */
    public JSONObject  getCommonParasm() {
        return ApiHeader.getCommonParasm(null);
    }

    public Map getCommonParamMap(HashMap<String,String> params){
        return ApiHeader.getCommonParamsMap(params);
    }
    /**
     * post请求
     *
     * @param url 请求url
     */
    public Observable<JSONObject> post(String url, HashMap<String,String> params) {
        JSONObject commonParasm = getCommonParasm(params);
        return Rx2AndroidNetworking.post(url)
                .setUserAgent(ApiHeader.getUserAgent())
                .addJSONObjectBody(commonParasm)
                .build()
                .getJSONObjectObservable();
    }

    public Observable<String> get(String url,HashMap<String,String> params){
        Map commonParam = getCommonParamMap(params);
        return Rx2AndroidNetworking.get(url)
            .setUserAgent(ApiHeader.getUserAgent())
            .addPathParameter(commonParam)
            .build()
            .getStringObservable();
    }
    /**
     * post请求
     *
     * @param url 请求url
     */
    public Observable<JSONObject> post(String url) {
        JSONObject  commonParasm= getCommonParasm();
        return Rx2AndroidNetworking.post(url)
                .setUserAgent(ApiHeader.getUserAgent())
                .addJSONObjectBody(commonParasm)
                .build()
                .getJSONObjectObservable();
    }

    /**
     * 上传图片
     * */
    public Rx2ANRequest.MultiPartBuilder upload(String url){
        OkHttpClient okHttpClient = OkHttpClientFactory.getInstance().initOkHttpUploadClient(Utils.getApp());
        return  Rx2AndroidNetworking.upload(url)
                .setUserAgent(ApiHeader.getUserAgent())
                .setOkHttpClient(okHttpClient);
    }
}
