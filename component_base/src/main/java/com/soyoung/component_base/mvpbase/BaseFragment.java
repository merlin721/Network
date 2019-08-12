package com.soyoung.component_base.mvpbase;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.soyoung.component_base.mvp.AbstractFragment;
import com.soyoung.component_base.mvp.view.BaseMvpView;
import com.soyoung.component_base.state_page.EmptyCallback;
import com.soyoung.component_base.state_page.LoadFailCallback;
import com.soyoung.component_base.state_page.LoadingCallback;
import com.soyoung.component_base.state_page.NoNetWorkCallback;
import com.soyoung.component_base.state_page.OverTimeCallback;
import com.soyoung.component_base.util.LogUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/10/30
 * description   : BaseFragment
 */

public abstract class BaseFragment extends AbstractFragment implements BaseMvpView {
    public BaseActivity mActivity;
    protected ViewGroup mRootView;
    protected ImmersionBar mImmersionBar;
    protected LoadService mBaseLoadService;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return false;
    }
    /**
     * 是否需要进行友盟页面统计
     * 默认是要统计
     * */
    protected boolean isUmengStatistics(){
        return true;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarWithKitkatEnable(false)
                .init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtils.e("===当前Fragment：" + this.getClass().getSimpleName());
        mRootView = (ViewGroup) inflater.inflate(setLayoutId(), container, false);
        initView();
        return mRootView;
    }


    protected abstract int setLayoutId();

    /**
     * 嵌入各类状态页
     */
    protected void initLoadRootView(View view) {
        if (null == view) {
            return;
        }
        mBaseLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRefreshData();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        setListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isUmengStatistics()) {
            MobclickAgent.onPageStart(this.getClass().getSimpleName()); //友盟页面
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isUmengStatistics()) {
            MobclickAgent.onPageEnd(this.getClass().getSimpleName()); //友盟页面
        }
    }

    /**
     * 设置各类监听事件
     */
    public void setListener() {
    }

    /**
     * 初始化数据
     */
    protected void initData(@Nullable Bundle savedInstanceState) {
    }

    /**
     * view与数据绑定
     */
    protected void initView() {
    }

    /**
     * 网络状态改变
     */
    @Override
    public void setNetworkChange(NetworkInfo networkInfo) {
        if (mActivity != null) {
            mActivity.setNetworkChange(networkInfo);
        }
    }

    /**
     * 网络状态改变 留于子类重写
     */
    public void onNetworkChange() {

    }

    /**
     * 刷新数据前准备检查工作
     */
    @Override
    public void onRefreshData() {
        boolean networkConnected = isNetworkConnected();
        if (!networkConnected) {
            showNoNetWork();
        } else {
            showLoading();
            onRequestData();
        }
    }

    /**
     * 刷新数据
     */
    public void onRequestData() {

    }

    /**
     * 加载页面
     */
    @Override
    public void showLoading() {
        if (null != mBaseLoadService) {
            mBaseLoadService.showCallback(LoadingCallback.class);
        }
    }

    /**
     * 成功页面
     */
    @Override
    public void showSuccess() {
        if (null != mBaseLoadService) {
            mBaseLoadService.showSuccess();
        }
    }

    /**
     * 空数据页面
     */
    @Override
    public void showEmpty() {
        if (null != mBaseLoadService) {
            mBaseLoadService.showCallback(EmptyCallback.class);
        }
    }

    /**
     * 加载失败
     */
    @Override
    public void showLoadingFail() {
        if (null != mBaseLoadService) {
            mBaseLoadService.showCallback(LoadFailCallback.class);
        }
    }

    /**
     * 无网络页面
     */
    @Override
    public void showNoNetWork() {
        if (null != mBaseLoadService) {
            mBaseLoadService.showCallback(NoNetWorkCallback.class);
        }
    }

    /**
     * 置空加载状态页
     */
    public void removeLoadPage() {
        showSuccess();
        mBaseLoadService = null;
    }

    @Override
    public void showLoadingDialog() {
        if (mActivity != null) {
            mActivity.showLoadingDialog();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (mActivity != null) {
            mActivity.hideLoadingDialog();
        }
    }

    /**
     * 超时页面
     */
    @Override
    public void showOverTime() {
        if (null != mBaseLoadService) {
            mBaseLoadService.showCallback(OverTimeCallback.class);
        }
    }

    @Override
    public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.onError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        super.onDetach();
    }

    public final <T extends View> T findViewById(int id) {
        if (!isDetached()) {
            return mRootView.findViewById(id);
        }

        return null;
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) mRootView.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(mRootView.getApplicationWindowToken(), 0);
        }
    }
}
