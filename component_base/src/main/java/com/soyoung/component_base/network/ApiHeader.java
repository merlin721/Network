package com.soyoung.component_base.network;

import android.text.TextUtils;

import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.UserDataSource;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.data.entity.UserEntity;
import com.soyoung.component_base.util.AppUtils;
import com.soyoung.component_base.util.DeviceUtils;
import com.soyoung.component_base.util.LogUtils;
import com.soyoung.component_base.util.Utils;
import com.soyoung.component_base.util.encode.MD5;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/2/27
 * description   : 请求头
 */
public class ApiHeader {
    public static final String PACKAGE_NAME_MOCK_DIARY = "com.youxiang.soyoungapp.diary";
    public static final String PACKAGE_NAME_TW = "com.youxiang.tw";
    public static final String PACKAGE_NAME_MOCK_PERI = "com.youxiang.soyoungapp.peri";
    private final PublicApiHeader mPublicApiHeader;

    private ApiHeader() {
        mPublicApiHeader = new PublicApiHeader();
    }

    public static ApiHeader getInstance() {
        return ApiHeaderLoader.INSTANCE;
    }

    public static String getUserAgent() {
        String userAgent = "";
        StringBuffer sb = new StringBuffer();
        try {
            userAgent = System.getProperty("http.agent");

            for (int i = 0, length = userAgent.length(); i < length; i++) {
                char c = userAgent.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    sb.append(String.format("\\u%04x", (int) c));
                } else {
                    sb.append(c);
                }
            }
        } catch (Exception e) {
            userAgent = "SoYoung";
            return userAgent;
        }
        return sb.toString();
    }

    public static JSONObject getCommonParasm(HashMap<String, String> hashMap) {
        SortedMap<String, String> params = new TreeMap<>();
        if (null != hashMap) {
            params.putAll(hashMap);
        }
        //String device_id = AppPreferencesHelper.getString(AppPreferencesHelper.DEVICE_ID);
        //String xy_device_token = AppPreferencesHelper.getString(AppPreferencesHelper.XY_DEVICE_TOKEN);
        //UserEntity user = UserDataSource.getInstance().getUser();
        //String uid = user.getUid();
        //params.put("uid", uid);
        //params.put("sys", "2");
        params.put("app_version", AppUtils.getAppVersionCode()+"");
        //params.put("device_id", device_id);
        if (TextUtils.isEmpty(params.get("token"))) {
            params.put("token", AppPreferencesHelper.getString(Constant.TOKEN));
        }
        params.put("app_source","2");
        params.put("app_system","2");
        params.put("app_device_id","");
        //params.put("token", "v_19f75ec5361e7d263fdc6072ee09cbfd_1_1539066677");
        //params.put("device_access_token", xy_device_token);
        //
        //params.put("app_id", "2");
        //if (TextUtils.isEmpty(params.get("lat"))) {
        //    String latitude = AppPreferencesHelper.getString(AppPreferencesHelper.LATITUDE);
        //    params.put("latitude", latitude);
        //}
        //if (TextUtils.isEmpty(params.get("lng"))) {
        //    String longitude = AppPreferencesHelper.getString(AppPreferencesHelper.LONGITUDE);
        //    params.put("longitude", longitude);
        //}
        long time = System.currentTimeMillis() / 1000;
        params.put("request_time", time + "");
        //List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(params.entrySet());
        //// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        //Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
        //    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
        //        return (o1.getKey()).compareTo(o2.getKey());
        //    }
        //});
        //
        //String paramsUrl = createParamsUrl(params);
        //params.put("signature", paramsUrl);
        JSONObject paramsJson = new JSONObject(params);
        LogUtils.e("getCommonParasm(ApiHeader.java:105)请求参数:\n" + paramsJson.toString());
        return paramsJson;
    }

    public static Map<String, String> getCommonParamsMap(HashMap<String, String> hashMap) {
        SortedMap<String, String> params = new TreeMap<>();
        if (null != hashMap) {
            params.putAll(hashMap);
        }
        //String device_id = AppPreferencesHelper.getString(AppPreferencesHelper.DEVICE_ID);
        //String xy_device_token = AppPreferencesHelper.getString(AppPreferencesHelper.XY_DEVICE_TOKEN);
        //UserEntity user = UserDataSource.getInstance().getUser();
        //String uid = user.getUid();
        //params.put("uid", uid);
        //params.put("sys", "2");
        //params.put("app_version", AppUtils.getAppVersionName());
        //params.put("device_id", device_id);
        //params.put("access_token", user.getAccess_token());
        //params.put("device_access_token", xy_device_token);
        //
        //params.put("app_id", "2");
        //if (TextUtils.isEmpty(params.get("lat"))) {
        //    String latitude = AppPreferencesHelper.getString(AppPreferencesHelper.LATITUDE);
        //    params.put("latitude", latitude);
        //}
        //if (TextUtils.isEmpty(params.get("lng"))) {
        //    String longitude = AppPreferencesHelper.getString(AppPreferencesHelper.LONGITUDE);
        //    params.put("longitude", longitude);
        //}
        //long time = System.currentTimeMillis() / 1000;
        //params.put("time", time + "");
        //List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(params.entrySet());
        //// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        //Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
        //    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
        //        return (o1.getKey()).compareTo(o2.getKey());
        //    }
        //});
        //
        //String paramsUrl = createParamsUrl(params);
        //params.put("signature", paramsUrl);
        //JSONObject paramsJson = new JSONObject(params);
        LogUtils.e("getCommonParasm(ApiHeader.java:105)请求参数:\n" + params.toString());
        return params;
    }

    /**
     * 使用 Map按key进行排序
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(
            new Comparator<String>() {
                @Override
                public int compare(String str1, String str2) {
                    return str1.compareTo(str2);
                }
            });

        sortMap.putAll(map);

        return sortMap;
    }

    private static String createParamsUrl(List<Map.Entry<String, String>> list) {
        StringBuffer paramsUrl = new StringBuffer();
        Iterator<Map.Entry<String, String>> entries = list.iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (!TextUtils.isEmpty(value)) {
                paramsUrl.append("&").append(key).append("=").append(value);
            } else {
                entries.remove();
            }
        }
        String str = paramsUrl.deleteCharAt(0).toString();
        String md5Params = MD5.md5_32(str);
        LogUtils.e("createParamsUrl(ApiHeader.java:114)" + str + "加密后:" + md5Params);
        return md5Params;
    }

    private static String createParamsUrl(Map<String, String> params) {
        StringBuffer paramsUrl = new StringBuffer();
        Iterator<Map.Entry<String, String>> entries = params.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (!TextUtils.isEmpty(value)) {
                paramsUrl.append("&").append(key).append("=").append(value);
            } else {
                entries.remove();
                params.remove(key);
            }
        }
        String str = paramsUrl.deleteCharAt(0).toString();
        String md5Params = MD5.md5_32(str);
        //LogUtils.e("createParamsUrl(ApiHeader.java:114)" + str + "加密后:" + md5Params);
        return md5Params;
    }

    private static String encodeParams(HashMap<String, String> hashMap) {
        if (null == hashMap) return null;
        StringBuilder paramsStr = new StringBuilder();
        Iterator<Map.Entry<String, String>> entries = hashMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (!TextUtils.isEmpty(value)) {
                paramsStr.append(key).append(value);
            } else {
                entries.remove();
                hashMap.remove(key);
            }
        }
        return MD5.md5_32(paramsStr.toString());
    }

    private static class ApiHeaderLoader {
        private static final ApiHeader INSTANCE = new ApiHeader();
    }

    public static final class PublicApiHeader {
        public HashMap<String, String> header_value;

        public PublicApiHeader() {
            header_value = getDefaultHeaderValue();
        }

        public HashMap<String, String> getHeader_value() {
            if (header_value.isEmpty()) {
                header_value = getDefaultHeaderValue();
            }
            return header_value;
        }

        public void updateHeaderUrl() {
            header_value = getDefaultHeaderValue();
        }

        private HashMap<String, String> getDefaultHeaderValue() {
            HashMap<String, String> headMap = new HashMap<>();
            headMap.put("User-Agent", getUserAgent());
            return headMap;
        }
    }
}
