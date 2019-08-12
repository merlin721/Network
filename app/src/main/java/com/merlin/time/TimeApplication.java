package com.merlin.time;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.androidnetworking.AndroidNetworking;
import com.kingja.loadsir.core.LoadSir;
import com.merlin.network.NetworkMgr;
import com.merlin.time.common.CommonUtils;
import com.merlin.time.utils.WXUtils;
import com.pgyersdk.crash.PgyCrashManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.soyoung.component_base.AppManager;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.header.ClassicsHeader;
import com.soyoung.component_base.network.AppBaseUrlConfig;
import com.soyoung.component_base.network.OkHttpClientFactory;
import com.soyoung.component_base.state_page.EmptyCallback;
import com.soyoung.component_base.state_page.LoadFailCallback;
import com.soyoung.component_base.state_page.LoadingCallback;
import com.soyoung.component_base.state_page.NoNetWorkCallback;
import com.soyoung.component_base.state_page.OverTimeCallback;
import com.soyoung.component_base.util.LogUtils;
import com.soyoung.component_base.util.Utils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lib.NetworkRequestUtil;
import lib.data.IpConfigBeen;
import okhttp3.OkHttpClient;


/**
 * @author zhouyang
 * @date 2018/10/28
 * @desc
 */
public class TimeApplication extends Application {
    /**
     * SmartRefreshLayout 全局设置header 和footer以及一些基础配置
     * 如果有特殊要求的要自己新添加自己的header并配置。
     */
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context,
                @NonNull RefreshLayout layout) {
                layout.setHeaderHeight(50);
                //设置回弹动画时长
                layout.setReboundDuration(50);
                //设置是否开启在刷新时候禁止操作内容视图
                layout.setDisableContentWhenRefresh(true);
                return new ClassicsHeader(context);
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            ClassicsFooter footer =
                new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            footer.setBackgroundColor(Color.WHITE);
            int color = Color.parseColor("#aaabb3");
            footer.setAccentColor(color);
            footer.setTextSizeTitle(14);
            //设置是否开启在刷新时候禁止操作内容视图
            layout.setDisableContentWhenLoading(true);
            return footer;
        });
    }

    @Override public void onCreate() {
        super.onCreate();
        Utils.init(this);
        initLog();
        initCallback();
        initFloatWindow(this);
        //CrashHandler.getInstance().init(this);
        initUMeng(this);
        //initBugly(this);
        OkHttpClient
            okHttpClient = OkHttpClientFactory.getInstance().initSSLOkHttpClient(this, !Constant.IS_DEBUG);
        AndroidNetworking.initialize(this,okHttpClient);
        NetworkMgr.getInstance().init(this,Constant.IS_DEBUG);
        PgyCrashManager.register(this);
        mTencent = Tencent.createInstance(CommonUtils.Tencent_APP_ID, this.getApplicationContext());
        initOss();
        WXUtils.regToWx(getApplicationContext());
    }
    public static Tencent mTencent;
    /**
     * 初始化友盟
     */
    private void initUMeng(Application application) {
        String channel = getChannel(application);
        UMConfigure.init(application, BuildConfig.UMENG_KEY, channel, UMConfigure.DEVICE_TYPE_PHONE, null);//基础组件初始化
        MobclickAgent.setScenarioType(application, MobclickAgent.EScenarioType.E_UM_NORMAL);//普通统计场景
        UMConfigure.setLogEnabled(Constant.IS_DEBUG);//打开统计SDK调试模式
        UMConfigure.setEncryptEnabled(!Constant.IS_DEBUG);//友盟日志加密
        MobclickAgent.openActivityDurationTrack(false);//关闭禁止默认的页面统计方式

    }

    private void initOss(){

    }
//    public static OSS oss;
    /**
     * 获取渠道信息
     */
    @NonNull
    private String getChannel(Application application) {
        String channel = "";//渠道信息
        if (TextUtils.isEmpty(channel)) {
            channel = "time";
        }
        return channel;
    }

    /**
     * Log打印初始
     */
    public void initLog() {
        LogUtils.Config config = LogUtils.getConfig()
            .setLogSwitch(Constant.IS_DEBUG)// 设置log总开关，包括输出到控制台和文件，默认开
            .setConsoleSwitch(Constant.IS_DEBUG)// 设置是否输出到控制台开关，默认开
            .setGlobalTag(null)// 设置log全局标签，默认为空
            // 当全局标签不为空时，我们输出的log全部为该tag，
            // 为空时，如果传入的tag为空那就显示类名，否则显示tag
            .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
            .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
            .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
            .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
            .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
            .setConsoleFilter(LogUtils.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
            .setFileFilter(LogUtils.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
            .setStackDeep(1);// log栈深度，默认为1
        LogUtils.d(config.toString());
    }

    /**
     * 初始化各种状态页面
     */
    private void initCallback() {
        LoadSir.beginBuilder()
            .addCallback(new LoadFailCallback())
            .addCallback(new EmptyCallback())
            .addCallback(new LoadingCallback())
            .addCallback(new OverTimeCallback())
            .addCallback(new NoNetWorkCallback())
            .setDefaultCallback(LoadingCallback.class)
            .commit();
    }

    /**
     * 初始化浮动窗口
     */
    private void initFloatWindow(Application application) {
        if (Constant.IS_DEBUG) {
            NetworkRequestUtil.getInstance()
                .setIpList(IPinit())
                .create(application)
                .setOnCheckBaseUrlListener(new NetworkRequestUtil.OnCheckBaseUrlListener() {
                    @Override
                    public void OnBaseUrlChange() {
                        AppManager.getAppManager().AppExit(application);
                        //程序退出 你这里要处理的操作
                        System.exit(0);
                    }

                    @Override public void changePage(String pagePath, Map<String, String> map) {

                    }
                });
        }
    }
    private ArrayList<IpConfigBeen> IPinit() {
        /*测试环境*/
        HashMap<String, String> testUrlMap = new HashMap<>();
        testUrlMap.put(AppBaseUrlConfig.BASE_URL, "http://time.taowangzhan.com");
        /*正式环境*/
        HashMap<String, String> releaseUrlMap = new HashMap<>();
        releaseUrlMap.put(AppBaseUrlConfig.BASE_URL, "http://time.taowangzhan.com");
        ArrayList<IpConfigBeen> list = new ArrayList<>();
        list.add(new IpConfigBeen("开发", testUrlMap));
        list.add(new IpConfigBeen("正式", releaseUrlMap));
        return list;
    }
}
