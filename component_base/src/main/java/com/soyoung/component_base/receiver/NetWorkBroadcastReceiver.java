package com.soyoung.component_base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.soyoung.component_base.mvp.view.BaseMvpView;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/2/27
 * description   : 网络状态监听
 */
public class NetWorkBroadcastReceiver extends BroadcastReceiver {
    private BaseMvpView MvpView;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null!=cm) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (null != MvpView) {
                MvpView.setNetworkChange(networkInfo);
            }
        }
    }
    public void setNetEvevt(BaseMvpView mvpView) {
        this.MvpView = mvpView;
    }
}
