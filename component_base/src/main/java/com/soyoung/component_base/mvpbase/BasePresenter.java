package com.soyoung.component_base.mvpbase;



import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.R;
import com.soyoung.component_base.mvp.presenter.BaseMvpPresenter;
import com.soyoung.component_base.mvp.view.BaseMvpView;
import com.soyoung.component_base.util.LogUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.net.ssl.SSLException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/10/30
 * description   : BasePresenter
 */

public class BasePresenter<V extends BaseMvpView> extends BaseMvpPresenter<V> {
    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;
    protected static final String RES_CODE_VALUE = "1";
    protected static final String RES_CODE_KEY = "resCode";
    private final CompositeDisposable mCompositeDisposable;

    public BasePresenter() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public static <T> ObservableTransformer<T, T> toMain() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @Override
    public void onDetachMvpView() {
        mCompositeDisposable.dispose();
        super.onDetachMvpView();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public boolean isViewAttached() {
        return getmMvpView() != null;
    }

    /**
     * 判断是否有网络
     */
    public boolean isNetWorke() {
        if (isViewAttached()) {
            return getmMvpView().isNetworkConnected();
        }
        return false;
    }

    /**
     * 网络是否可用
     */
    protected void showNotNetWork() {
        if (isViewAttached()) {
            getmMvpView().showNoNetWork();
        }
    }

    /**
     * 数据为空
     */
    protected void showEmpty() {
        if (isViewAttached()) {
            getmMvpView().showEmpty();
        }
    }

    /**
     * 正在加载
     */
    protected void showLoading() {
        if (isViewAttached()) {
            getmMvpView().showLoading();
        }
    }

    /**
     * 成功
     */
    protected void showSuccess() {
        if (isViewAttached()) {
            getmMvpView().showSuccess();
        }
    }
    protected void removeLoadPage(){
        if (isViewAttached()) {
            getmMvpView().removeLoadPage();
        }
    }

    /**
     * 失败
     */
    protected void showLoadingFail() {
        if (isViewAttached()) {
            getmMvpView().showLoadingFail();
        }
    }

    /**
     * 超时
     */
    protected void showOverTime() {
        if (isViewAttached()) {
            getmMvpView().showOverTime();
        }
    }

    /**
     * toast
     */
    protected void showMessage(String message) {
        if (isViewAttached()) {
            getmMvpView().showMessage(message);
        }
    }

    protected void showMessage(int resID) {
        if (isViewAttached()) {
            getmMvpView().showMessage(resID);
        }
    }

    protected void hideLoadingDialog() {
        if (isViewAttached()) {
            getmMvpView().hideLoadingDialog();
        }
    }

    protected void showLoadingDialog() {
        if (isViewAttached()) {
            getmMvpView().showLoadingDialog();
        }
    }


    public void handleApiError(Throwable throwable) {

        hideLoadingDialog();
        if (Constant.IS_DEBUG) {
            Writer result = new StringWriter();
            PrintWriter printWriter = new PrintWriter(result);
            throwable.printStackTrace(printWriter);
            LogUtils.e("handleApiError()" + result.toString());
        }

        if (throwable instanceof ANError) {

            ANError error = (ANError) throwable;
            if (error.getErrorCode() == API_STATUS_CODE_LOCAL_ERROR
                    && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                showMessage(R.string.connection_error);
                showOverTime();
                return;
            }
            showLoadingFail();
            if (error.getErrorBody() == null) {
                showMessage(R.string.api_default_error);
                return;
            }
            if (error.getErrorCode() == API_STATUS_CODE_LOCAL_ERROR
                    && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR)) {
                showMessage(R.string.api_retry_error);
                return;
            }
            if (error.getErrorCode() > 400) {
                showMessage("code:" + error.getErrorCode());
            }
        }else {
            if (throwable instanceof SSLException){
                showMessage(R.string.ssl_error);
            }
            showLoadingFail();
        }

    }
}
