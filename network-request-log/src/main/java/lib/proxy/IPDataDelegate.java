package lib.proxy;

import java.util.ArrayList;

import lib.data.IpConfigBeen;

/**
 * IPDataDelegate
 *
 * @author ：daiwenbo
 * @Time ：2018/6/9 下午4:17
 * @e-mail ：daiwwenb@163.com
 * @description ：ip 数据处理 中间类
 */
public class IPDataDelegate {

    private ArrayList<IpConfigBeen> ipConfigs;

    public IPDataDelegate() {
        ipConfigs = new ArrayList<>();
    }

    public void putAppIp(ArrayList<IpConfigBeen> arrayList) {
        if (arrayList.size() > 0) {
            ipConfigs.addAll(arrayList);
        }
    }

    public ArrayList<IpConfigBeen> getAppIp() {
        return ipConfigs;
    }

    public synchronized void deleteIp(int index) {
        if (ipConfigs.size() > 0) {
            ipConfigs.remove(index);
        }
    }

    public synchronized void deleteIpAll() {
        if (ipConfigs.size() > 0) {
            ipConfigs.clear();
        }
    }





}