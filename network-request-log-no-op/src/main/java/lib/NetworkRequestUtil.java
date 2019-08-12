package lib;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.data.IpConfigBeen;

/**
 * @author liuml
 * @explain 测试库工具 关于工具库所有的操作都通过这个类
 * @time 2016/12/7 10:04
 */

public class NetworkRequestUtil {
    OnCheckBaseUrlListener onCheckBaseUrlListener;

    public NetworkRequestUtil setIpList(ArrayList<IpConfigBeen> arrayList) {
        return this;
    }

    public void setOnCheckBaseUrlListener(OnCheckBaseUrlListener onCheckBaseUrlListener) {
    }

    private static class InnerInstance {
        protected static NetworkRequestUtil instance = new NetworkRequestUtil();
    }

    public static NetworkRequestUtil getInstance() {
        return InnerInstance.instance;
    }

    public NetworkRequestUtil() {

    }

    /**
     * 显示浮窗
     */
    public NetworkRequestUtil create(Application context) {

        return this;
    }

    /**
     * 显示
     */
    public void showFloatingMenu(Context context) {
    }

    /**
     * 隐藏
     */
    public void hideFloatingMenu() {
    }

    /**
     * 保存错误日志
     *
     * @param ex
     */
    public void saveException(String ex) {

    }

    public int getSwitchIndex() {
        return 0;
    }
    public ArrayList<IpConfigBeen> getIpList() {
        return null;
    }

    public boolean isReleaseIp(){
        return true;
    }

    public void stopService(Context context) {
    }


    public void setCheckBaseUrlListener() {

    }

    public String getCrashFilePath() {
        return "";
    }

    /**
     * 获取设备详情
     */
    public String getDeviceInfo() {
        return "";
    }


    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {

        return false;
    }

    /**********************************************ip 操作******************************************************/

    /**
     * 获取当前ip
     *
     * @return
     */
    public HashMap<String, String> getSwitchIP() {
        return null;
    }


    /**
     * 设置选中的ip
     */
    public void setSwitchIp(int index) {

    }


    public interface OnCheckBaseUrlListener {
        void OnBaseUrlChange();

        void changePage(String pagePath, Map<String, String> map);
    }


}
