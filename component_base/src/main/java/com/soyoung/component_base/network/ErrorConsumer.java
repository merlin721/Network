package com.soyoung.component_base.network;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.R;
import com.soyoung.component_base.mvp.view.BaseMvpView;
import com.soyoung.component_base.mvpbase.BasePresenter;
import com.soyoung.component_base.util.LogUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.net.ssl.SSLException;

import io.reactivex.functions.Consumer;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/9/27
 * description   : 整个网络请求 错误处理类
 */

public abstract class ErrorConsumer implements Consumer<Throwable> {
    private static final int API_STATUS_CODE_LOCAL_ERROR = 0;
    private BasePresenter presenter;
    private BaseMvpView baseMvpView;

    /**
     * 其他地方使用
     */
    public ErrorConsumer() {
    }

    /**
     * Presenter 中使用
     * @param presenter presenter
     */
    public ErrorConsumer(BasePresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * activity 或者 fragment 中使用
     * @param mvpView mvpView
     */
    public ErrorConsumer(BaseMvpView mvpView) {
        this.baseMvpView = mvpView;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        handleApiError(throwable);
        onDone();
    }
    public void onDone(){

    }

    private void handleApiError(Throwable throwable) {
        if (Constant.IS_DEBUG) {
            Writer result = new StringWriter();
            PrintWriter printWriter = new PrintWriter(result);
            throwable.printStackTrace(printWriter);
            LogUtils.e("handleApiError()" + result.toString());
        }
        if (null!=presenter) {
            baseMvpView = presenter.getmMvpView();
        }

        if (null == baseMvpView) return;

        baseMvpView.hideLoadingDialog();

        if (throwable instanceof ANError) {

            ANError error = (ANError) throwable;
            if (error.getErrorCode() == API_STATUS_CODE_LOCAL_ERROR
                    && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                baseMvpView.showMessage(R.string.connection_error);
                baseMvpView.showOverTime();
                return;
            }
            baseMvpView.showLoadingFail();
            if (error.getErrorBody() == null) {
                baseMvpView.showMessage(R.string.api_default_error);
                return;
            }
            if (error.getErrorCode() == API_STATUS_CODE_LOCAL_ERROR
                    && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR)) {
                baseMvpView.showMessage(R.string.api_retry_error);
                return;
            }
            if (error.getErrorCode() > 400) {
                baseMvpView.showMessage("code:" + error.getErrorCode());
            }
        } else {
            if (throwable instanceof SSLException) {
                baseMvpView.showMessage(R.string.ssl_error);
            }
            baseMvpView.showLoadingFail();
        }

    }
}
