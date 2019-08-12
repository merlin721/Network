package lib.proxy;

import android.content.Context;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import lib.data.HttpTransaction;
import lib.data.IpConfigBeen;


/**
 * @author liuml
 * @explain 当前库的服务器IP设置
 * @time 2016/12/8 14:17
 */

public class CommonDelegate {
    private IPDataDelegate dataSave;
    private ArrayList<HttpTransaction> httpQueue;
    private CrashHelper crashHelper;
    private final ArouterDelegate arouterDelegate;

    public CommonDelegate(Context context) {
        httpQueue = new ArrayList<>();
        dataSave = new IPDataDelegate();
        crashHelper = new CrashHelper(context);
        arouterDelegate = new ArouterDelegate();
    }

    /**
     * ip 初始化 存入list集合
     *
     * @param list
     */
    public void initIpConfig(ArrayList<IpConfigBeen> list) {
        dataSave.putAppIp(list);
    }

    /**
     * 获取选中的IP
     */
    public HashMap<String, String> getSwitchIp(int index) {
        IpConfigBeen ipConfigBeen = null;
        ArrayList<IpConfigBeen> ipList = getIpList();
        if (ipList.size() > 0) {
            try {
                ipConfigBeen = ipList.get(index);
            } catch (Exception ex) {
                ex.printStackTrace();
                ipConfigBeen = ipList.get(0);
            }
        }
        if (null != ipConfigBeen) {
            return ipConfigBeen.getUrlMap();
        }
        return null;
    }

    /**
     * 获取全部ip
     *
     * @return
     */
    public ArrayList<IpConfigBeen> getIpList() {
        return dataSave.getAppIp();
    }


    public boolean addNetWorkMessage(String header, String url, String json) {
        //直接操作静态变量
        HttpTransaction been = new HttpTransaction();
        been.setUrl(url);
        been.setRequestBody("header = " + header);
        been.setResponseBody(json);
        //最大条数  0条避免数量过多溢出
        if (httpQueue.size() >= 20) {
            httpQueue.remove(0);
        }
        return httpQueue.add(been);
    }

    public boolean addNetWorkMessage(HttpTransaction httpBeen) {
        //最大条数  0条避免数量过多溢出
        if (httpQueue.size() >= 20) {
            httpQueue.remove(0);
        }
        return httpQueue.add(httpBeen);
    }
    public void clearNetworkRequest() {
        if (httpQueue.size() > 0) {
            httpQueue.clear();
        }
    }

    public ArrayList<HttpTransaction> getRequestData() {
        ArrayList<HttpTransaction> httpTransactions=new ArrayList<>();
        httpTransactions.addAll(httpQueue);
        Collections.reverse(httpTransactions);
        return httpTransactions;
    }

    public HashMap<String, String> getArouterData(Context context) {
        return arouterDelegate.readProperties(context);
    }

    public void saveCrashInfo(String ex) {
        crashHelper.saveCrashInfo(ex);
    }

    public String getCrashFilePath() {
        return crashHelper.getCrashFilePath();
    }

    public String getDeviceInfo(){
        return crashHelper.getDeviceInfo().toString();
    }
}
