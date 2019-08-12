package com.soyoung.component_base.mvpbase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.soyoung.component_base.util.swip.SwipeBackActivityHelper;
import com.gyf.barlibrary.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.soyoung.component_base.AppManager;
import com.soyoung.component_base.R;
import com.soyoung.component_base.mvp.AbstractMvpActivity;
import com.soyoung.component_base.mvp.view.BaseMvpView;
import com.soyoung.component_base.receiver.NetWorkBroadcastReceiver;
import com.soyoung.component_base.state_page.EmptyCallback;
import com.soyoung.component_base.state_page.LoadFailCallback;
import com.soyoung.component_base.state_page.LoadingCallback;
import com.soyoung.component_base.state_page.NoNetWorkCallback;
import com.soyoung.component_base.state_page.OverTimeCallback;
import com.soyoung.component_base.util.LogUtils;
import com.soyoung.component_base.util.NetworkUtils;
import com.soyoung.component_base.util.ToastUtils;
import com.soyoung.component_base.util.swip.SwipeBackLayout;
import com.soyoung.component_base.widget.CustomTitleBar;
import com.soyoung.component_base.widget.LoadingDialog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * BaseActivity
 *
 * @author ：daiwenbo
 * @Time ：2018/2/27
 * @e-mail ：daiwwenb@163.com
 * @description ：BaseActivity
 */
public abstract class BaseActivity extends AbstractMvpActivity implements BaseMvpView {
    /**
     * 友盟
     * 统计 埋点 页面
     */
    protected String UM_PAGE_NAME;
    /**
     * 跳转到GPS开关界面
     */
    public static final int REQUEST_CODE_LOCATION_SETTINGS = 700;
    public CustomTitleBar titleLayout;
    public Context context;
    public View mRootView;
    protected ImmersionBar mImmersionBar;
    protected LoadService mBaseLoadService;
    private CompositeDisposable mCompositeDisposable;
    /**
     * 网络状态改变 flag
     */
    private boolean netWorkIsAvailable = true;
    private NetWorkBroadcastReceiver receiver;
    private LoadingDialog loadingDialog;
    //private SwipeBackActivityHelper mHelper;

    public static <T> ObservableTransformer<T, T> toMain() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 是否在Activity使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 该activity 是否初始化初始化左滑关闭
     */
    protected boolean isInitSwipeBackActivity() {
        return true;
    }

    /**
     * 是否注册EventBus
     * 默认不注册
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    /**
     * 是否包含fragment
     */
    protected boolean isContainFragment() {
        return false;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
            .statusBarDarkFont(true, 0.2f)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.ffffff)
            .navigationBarWithKitkatEnable(false)
            .init();
    }

    /**
     * 初始化 各种状态页
     */
    protected void initCallbackView() {
        mBaseLoadService = LoadSir.getDefault().register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRefreshData();
            }
        });
    }

    /**
     * 初始化 各种状态页(指定View)
     */
    protected void initCallbackView(View view) {
        mBaseLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRefreshData();
            }
        });
    }

    /**
     * 适用于MVC 模式的网络请求管理
     */
    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        context = this;
        mCompositeDisposable = new CompositeDisposable();
        mRootView = LayoutInflater.from(this).inflate(setLayoutId(), null);
        setContentView(mRootView);
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
        UM_PAGE_NAME = getClass().getSimpleName();
        LogUtils.e("===当前activity：(" + UM_PAGE_NAME + ".java:1)");
        if (!"MainActivity".equalsIgnoreCase(this.getClass().getSimpleName())) {
            AppManager.getAppManager().addActivity(this);
        }
        titleLayout = findViewById(R.id.title_layout);
        /*初始化左滑关闭*/
        initSwipeBackActivity();
        //初始化view
        initView();
        //初始化数据绑定
        initData(savedInstanceState);
        //设置监听
        setListener();
        //网络状态改变监听
        registerBroadrecevicer();
        //初始化沉浸式 要在初始化控件后边
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }
    private SwipeBackActivityHelper mHelper;
    /**
     * 初始化左滑关闭
     */
    private void initSwipeBackActivity() {
        if (!isInitSwipeBackActivity()) {
            return;
        }
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        mHelper.getSwipeBackLayout()
            .addSwipeListener(new SwipeBackLayout.SwipeListener() {
                @Override
                public void onScrollStateChange(int state, float scrollPercent) {
                    try {
                        if (getCurrentFocus() == null) {
                            return;
                        }
                        // 隐藏输入法
                        InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(
                            getCurrentFocus().getApplicationWindowToken()/*.getWindowToken()*/, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onEdgeTouch(int edgeFlag) {

                }

                @Override
                public void onScrollOverThreshold() {

                }
            });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (null != mHelper) {
            mHelper.onPostCreate();
        }
    }


    public void setSwipeBackEnable(boolean enable) {
        if (null != mHelper) {
            mHelper.getSwipeBackLayout().setEnableGesture(enable);
        }
    }

    @Override
    public void setNetworkChange(NetworkInfo networkInfo) {
        if (networkInfo != null) {
            if (networkInfo.isConnected()) {
                //说明网络是连接的
                NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType();
                if (NetworkUtils.NetworkType.NETWORK_2G == networkType) {
                    showMessage("网络太差");
                }
                if (!netWorkIsAvailable) {
                    onNetworkChange();
                }
                netWorkIsAvailable = networkInfo.isConnected();
            }
        } else {
            netWorkIsAvailable = false;
            LogUtils.e("setNetworkChange(BaseActivity.java:163)网络已断开");
            showMessage(R.string.network_is_not_connected);
            showNoNetWork();
        }
    }

    /**
     * 网络状态改变 留于子类重写
     */
    protected void onNetworkChange() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//友盟统计时长
        if (!isContainFragment()) {
            MobclickAgent.onPageStart(UM_PAGE_NAME); //友盟页面
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);//友盟统计时长
        if (!isContainFragment()) {
            MobclickAgent.onPageEnd(UM_PAGE_NAME); //友盟页面
        }
        hideKeyboard();
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.dispose();
        super.onStop();
    }

    /**
     * 网络状态改变监听广播注册
     */
    private void registerBroadrecevicer() {
        receiver = new NetWorkBroadcastReceiver();
        receiver.setNetEvevt(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    /**
     * 网络状态改变监听广播解注册
     */
    private void unregisterReceiver() {
        receiver.setNetEvevt(null);
        unregisterReceiver(receiver);
        receiver = null;
    }

    /**
     * 初始化数据
     */
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 初始化view
     */
    protected void initView() {
    }

    /**
     * 设置监听
     */
    protected void setListener() {
        if (titleLayout != null) {
            titleLayout.setTitleClickListener(new CustomTitleBar.TitleUpdateListener() {
                @Override
                public void onLeftClick() {
                    finish();
                }
            });
        }
    }

    protected abstract int setLayoutId();

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
     * 超时页面
     */
    @Override
    public void showOverTime() {
        if (null != mBaseLoadService) {
            mBaseLoadService.showCallback(OverTimeCallback.class);
        }
    }

    @Override
    public void showLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog.Builder(this).create();
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 数据加载成功 置空加载状态页
     */
    @Override
    public void removeLoadPage() {
        showSuccess();
        mBaseLoadService = null;
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            ToastUtils.showToast(this, message);
        } else {
            ToastUtils.showToast(this, R.string.some_error);
        }
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            ToastUtils.showToast(this, message);
        } else {
            ToastUtils.showToast(this, R.string.some_error);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isConnected();
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (getIsFinishImmerStatus()) {
            if (mImmersionBar != null) {
                mImmersionBar.destroy();
            }
        }
        AppManager.getAppManager().deleteActivity(this);
        hideLoadingDialog();
        unregisterReceiver();//解注册
        super.onDestroy();
    }

    /**
     * 打开GPS 开关界面
     */
    public void openGPS() {
        new AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("GPS提示")
            .setMessage("请打开GPS,以便更好的为你服务")
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 转到手机设置界面，用户设置GPS
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, REQUEST_CODE_LOCATION_SETTINGS);
                }
            })
            .setNegativeButton("取消", null)
            .show();
    }

    /**
     * 移动数据 界面
     */
    public void openNetWorkPage() {
        new AlertDialog.Builder(this)
            .setCancelable(false)
            .setMessage("网络无法访问,请检查网络连接")
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 转到手机设置界面，用户设置网络
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivityForResult(intent, REQUEST_CODE_LOCATION_SETTINGS);
                }
            })
            .setNegativeButton("取消", null)
            .show();
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        InputMethodManager imm = (InputMethodManager)
            getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    public boolean getIsFinishImmerStatus() {
        return true;
    }
}
