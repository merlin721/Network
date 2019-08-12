package com.soyoung.component_base.network;

import android.text.TextUtils;

import com.soyoung.component_base.BuildConfig;
import com.soyoung.component_base.Constant;
import java.util.HashMap;

import lib.NetworkRequestUtil;

/**
 * AppBaseUrlConfig
 *
 * @author ：daiwenbo
 * @Time ：2018/6/21 下午2:03
 * @e-mail ：daiwwenb@163.com
 * @description ：这个项目的baserUrl配置
 */
public class AppBaseUrlConfig {
    public static final String BASE_URL = "base_url";
    /**
     * 获取BaseUrl
     * */
    public static String getBaseUrl() {
        String base_url = null;
        if (Constant.IS_DEBUG) {
            base_url = getSelectedUrl(BASE_URL);
            if (TextUtils.isEmpty(base_url)) {
                base_url = BuildConfig.BASE_URL;
            }
        } else {
            base_url = BuildConfig.BASE_URL;
        }
        return base_url;
    }

    private static String getSelectedUrl(String key) {
        HashMap<String, String> switchIP = NetworkRequestUtil.getInstance().getSwitchIP();
        if (null==switchIP||switchIP.isEmpty()) {
            return null;
        }
        return switchIP.get(key);
    }


}
