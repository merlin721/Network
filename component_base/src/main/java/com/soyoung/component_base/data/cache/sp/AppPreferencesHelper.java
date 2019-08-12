package com.soyoung.component_base.data.cache.sp;

import android.support.annotation.NonNull;
import java.util.Map;
import java.util.Set;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/10/23
 * description   : sp
 */

public class AppPreferencesHelper {
    private static final String AES_KEY = "96375148";

    /******************************************设备注册相关**********************************************/
    public static final String DEVICE_ID = "device_id";
    public static final String XY_DEVICE_TOKEN = "xy_device_token";
    public static final String HTTP_SIGN = "http_sign";
    public static final String CHECK_SSL = "check_ssl";//保存是否需要 做SSL 单向验证


    /******************************************用户个人信息相关**********************************************/
    public static final String USER_NAME = "user_name";
    public static final String USER_ID = "userid";//用户ID
    public static final String LOGIN_MOBILE = "login_mobile";//手机
    /*城市*/
    public static final String CITY = "city";
    /*精度*/
    public static final String LONGITUDE = "longitude";
    /*纬度*/
    public static final String LATITUDE = "Latitude";
    /*省份*/
    public static final String PROVINCE = "province";


    private AppPreferencesHelper() {
    }


    public static void put(@NonNull String key, @NonNull String value) {
        AppSpHelper.getInstance().put(key, value);
    }

    public static String getString(@NonNull String key) {
        return AppSpHelper.getInstance().getString(key);
    }


    public static String getString(@NonNull String key, @NonNull String defaultValue) {
        return AppSpHelper.getInstance().getString(key, defaultValue);
    }


    public static void put(@NonNull String key, int value) {
        AppSpHelper.getInstance().put(key, value);
    }


    public static int getInt(@NonNull String key) {
        return AppSpHelper.getInstance().getInt(key);
    }


    public static int getInt(@NonNull String key, int defaultValue) {
        return AppSpHelper.getInstance().getInt(key, defaultValue);
    }


    public static void put(@NonNull String key, long value) {
        AppSpHelper.getInstance().put(key, value);
    }


    public static long getLong(@NonNull String key) {
        return AppSpHelper.getInstance().getLong(key);
    }


    public static long getLong(@NonNull String key, long defaultValue) {
        return AppSpHelper.getInstance().getLong(key, defaultValue);
    }


    public static void put(@NonNull String key, float value) {
        AppSpHelper.getInstance().put(key, value);
    }


    public static float getFloat(@NonNull String key) {
        return AppSpHelper.getInstance().getFloat(key);
    }


    public static float getFloat(@NonNull String key, float defaultValue) {
        return AppSpHelper.getInstance().getFloat(key, defaultValue);
    }


    public static void put(@NonNull String key, boolean value) {
        AppSpHelper.getInstance().put(key, value);
    }


    public static boolean getBoolean(@NonNull String key) {
        return AppSpHelper.getInstance().getBoolean(key);
    }


    public static boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return AppSpHelper.getInstance().getBoolean(key, defaultValue);
    }


    public static void put(@NonNull String key, @NonNull Set<String> values) {
        AppSpHelper.getInstance().put(key, values);
    }


    public static Set<String> getStringSet(@NonNull String key) {
        return AppSpHelper.getInstance().getStringSet(key);
    }


    public static Set<String> getStringSet(@NonNull String key, @NonNull Set<String> defaultValue) {
        return AppSpHelper.getInstance().getStringSet(key, defaultValue);
    }


    public static Map<String, ?> getAll() {
        return AppSpHelper.getInstance().getAll();
    }


    public static void remove(@NonNull String key) {
        AppSpHelper.getInstance().remove(key);
    }


    public static boolean contains(@NonNull String key) {
        return AppSpHelper.getInstance().contains(key);
    }


    public static void clear() {
        AppSpHelper.getInstance().clear();
    }
}
