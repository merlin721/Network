package com.merlin.time.utils;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author merlin720
 * @date 2019/3/4
 * @mail zy44638@gmail.com
 * @description
 */
public class WXUtils {

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wx93dcff7f0e81e7b5";
    // IWXAPI 是第三方app和微信通信的openApi接口
    public static IWXAPI api;

    public static void regToWx(Context context) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

}
