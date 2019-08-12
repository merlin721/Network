package lib;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import httploglib.lib.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.appstate.AppStateTracker;
import lib.data.CloseMenu;
import lib.data.HttpTransaction;
import lib.data.IpConfigBeen;
import lib.proxy.CommonDelegate;
import lib.theming.HoverTheme;
import lib.theming.HoverThemeManager;

/**
 * NetworkRequestUtil
 *
 * @author ：daiwenbo
 * @Time ：2018/6/13 下午2:25
 * @e-mail ：daiwwenb@163.com
 * @description ：NetworkRequestUtil
 */
public class NetworkRequestUtil {
    private static final String TAG = NetworkRequestUtil.class.getSimpleName();
    private static final String sp_file_name = "network_log";
    private static final String sp_ip_key = "ip_key";
    CommonDelegate commonDelegate;
    ArrayList<IpConfigBeen> ipLists;
    OnCheckBaseUrlListener onCheckBaseUrlListener;
    private Application context;

    private NetworkRequestUtil() {

    }

    public void setOnCheckBaseUrlListener(OnCheckBaseUrlListener onCheckBaseUrlListener) {
        this.onCheckBaseUrlListener = onCheckBaseUrlListener;
    }

    public void changePage(String arouterPath, Map<String, String> map) {
        if (null != onCheckBaseUrlListener) {
            EventBus.getDefault().post(new CloseMenu(1));
            onCheckBaseUrlListener.changePage(arouterPath, map);
        }
    }


    public static NetworkRequestUtil getInstance() {
        return InnerInstance.instance;
    }


    /**
     * 显示浮窗
     */
    public NetworkRequestUtil create(Application context) {
        this.context = context;
        commonDelegate = new CommonDelegate(context);
        if (null != ipLists) {
            commonDelegate.initIpConfig(ipLists);
        }
        setupAppStateTracking();
        return this;
    }

    /**
     * 显示
     */
    public void showFloatingMenu(Context context) {
        DemoHoverMenuService.showFloatingMenu(context);
    }

    /**
     * 停止服务
     */
    public void stopService(Context context) {
        DemoHoverMenuService.hideFloatingMenu(context);
    }

    /**
     * 显示
     */
    public void hideFloatingMenu() {
        EventBus.getDefault().post(new CloseMenu(1));
    }

    /**
     * 保存错误日志
     *
     * @param result
     */
    public void saveException(String result) {
        stopService(context);
        if (null != commonDelegate) {
            commonDelegate.saveCrashInfo(result);
        }
    }


    public void setCheckBaseUrlListener() {
        stopService(context);
        if (null != onCheckBaseUrlListener) {
            onCheckBaseUrlListener.OnBaseUrlChange();
        }
    }

    public String getCrashFilePath() {
        return commonDelegate.getCrashFilePath();
    }

    /**
     * 获取设备详情
     */
    public String getDeviceInfo() {
        return commonDelegate.getDeviceInfo();
    }

    /**
     * 发送网路哦请求
     */
    public void addNetWorkMessage(String header, String url, String json) {
        commonDelegate.addNetWorkMessage(header, url, json);
    }

    /**
     * 发送网路哦请求
     */
    public void addNetWorkMessage(HttpTransaction httpBeen) {
        commonDelegate.addNetWorkMessage(httpBeen);
    }

    public ArrayList<HttpTransaction> getRequestData() {
        return commonDelegate.getRequestData();
    }

    public void clearNetworkRequest() {
        commonDelegate.clearNetworkRequest();
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 获取当前ip
     */
    public HashMap<String, String> getSwitchIP() {
        int switchIndex = getSwitchIndex();
        return commonDelegate.getSwitchIp(switchIndex);
    }

    /**********************************************ip 操作******************************************************/

    public int getSwitchIndex() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(sp_file_name, Context.MODE_PRIVATE);
        int anInt = sharedPreferences.getInt(sp_ip_key, 1);
        return anInt;
    }

    /**
     * 设置选中的ip
     */
    public void setSwitchIp(int index) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(sp_file_name, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(sp_ip_key, index).commit();
        setCheckBaseUrlListener();
    }

    public boolean isReleaseIp() {
        int switchIndex = getSwitchIndex();
        ArrayList<IpConfigBeen> ipList = getIpList();
        if (null==ipList){
            return true;
        }
        return switchIndex == ipList.size() - 1;
    }

    /**
     * 获取所有ip地址
     */
    public ArrayList<IpConfigBeen> getIpList() {
        return commonDelegate.getIpList();
    }

    public NetworkRequestUtil setIpList(ArrayList<IpConfigBeen> arrayList) {
        ipLists = arrayList;
        return this;
    }

    private void setupAppStateTracking() {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //整个app 的状态跟踪器
        AppStateTracker.init(context);
        //获取栈顶的activity 的id 以及名字
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (activityManager.getAppTasks().size() > 0) {
                AppStateTracker.getInstance()
                        .trackTask(activityManager.getAppTasks().get(0).getTaskInfo());
            }
        }
    }

    public static void copy(Context context, String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
        ClipData clipData = ClipData.newPlainText(null, content);
        if (null != cmb) {
            cmb.setPrimaryClip(clipData);
            Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
        }
    }

    public HashMap<String, String> getArouterData(Context context) {
        return commonDelegate.getArouterData(context);
    }

    public interface OnCheckBaseUrlListener {
        void OnBaseUrlChange();

        void changePage(String pagePath, Map<String, String> map);
    }

    private static class InnerInstance {
        protected static NetworkRequestUtil instance = new NetworkRequestUtil();
    }
}
