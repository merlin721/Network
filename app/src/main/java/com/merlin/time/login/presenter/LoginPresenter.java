package com.merlin.time.login.presenter;

import com.merlin.time.login.LoginView;
import com.merlin.time.network.AppNetWorkHelper;
import com.soyoung.component_base.mvpbase.BasePresenter;
import com.soyoung.component_base.network.ErrorConsumer;
import com.soyoung.component_base.util.ToastUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.json.JSONObject;

/**
 * @author zhouyang
 * @date 2018/10/28
 * @desc
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    //获取验证码
    public void login(String phoneNum){
        Disposable disposable = AppNetWorkHelper.getInstance()
            .sendCode(phoneNum)
            .compose(toMain())
            .subscribe(
                new Consumer<JSONObject>() {
                    @Override public void accept(JSONObject o) throws Exception {
                        String errorCode = o.optString("ret");
                        if (errorCode.equals("1")) {
                            getmMvpView().goCode(phoneNum);
                        } else {
                            JSONObject jsonObject = new JSONObject(o.optString("error"));
                            String msg = jsonObject.optString("msg");
                            getmMvpView().showMessage(msg);
                        }
                    }
                }, new ErrorConsumer() {
                    @Override public void onDone() {
                        super.onDone();
                    }
                });
        getCompositeDisposable().add(disposable);
    }
}
