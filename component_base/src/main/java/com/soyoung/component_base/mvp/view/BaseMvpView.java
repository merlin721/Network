package com.soyoung.component_base.mvp.view;

import android.net.NetworkInfo;
import android.support.annotation.StringRes;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/2/27
 * description   : base MvpView
 */
public interface BaseMvpView {
    /**加载中*/
    void showLoading();
    /**加载成功*/
    void showSuccess();
    /**加载失败*/
    void showLoadingFail();
    /**没有网络*/
    void showNoNetWork();
    /**请求超时*/
    void showOverTime();
    /**没有数据*/
    void showEmpty();
    /**隐藏软键盘*/
    void hideKeyboard();
    /**刷新数据*/
    void onRefreshData();

    void removeLoadPage();

    /**全局统一 加载Dialog*/
    void showLoadingDialog();

    void hideLoadingDialog();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void setNetworkChange(NetworkInfo networkInfo);
}
