package com.merlin.time.login;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.merlin.time.login.model.LoginCodeModel;
import com.merlin.time.network.AppNetWorkHelper;
import com.soyoung.component_base.mvpbase.BasePresenter;
import com.soyoung.component_base.network.ErrorConsumer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.json.JSONObject;

/**
 * @author zhouyang
 * @date 2018/10/28
 * @desc
 */
public class LoginCodePresenter extends BasePresenter<LoginCodeView> {

    //获取验证码
    void getCode(String phoneNum){
        Disposable disposable = AppNetWorkHelper.getInstance()
            .sendCode(phoneNum)
            .compose(toMain())
            .subscribe(
                new Consumer<JSONObject>() {
                    @Override public void accept(JSONObject o) throws Exception {
                        String errorCode = o.optString("ret");
                        if (errorCode.equals("1")) {
                            getmMvpView().getCode();
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
    void checkCode(String phone,String code){
        Disposable disposable =AppNetWorkHelper.getInstance().checkCode(phone,code)
            .compose(toMain())
            .subscribe(new Consumer<JSONObject>() {
                @Override public void accept(JSONObject jsonObject) throws Exception {
                    String errorCode = jsonObject.optString("ret");
                    if (errorCode.equals("1")) {
                        String data = jsonObject.optString("data");
                        if (!TextUtils.isEmpty(data)){
                            Gson gson = new Gson();
                            LoginCodeModel model = gson.fromJson(data,LoginCodeModel.class);
                            getmMvpView().goMain(model);
                        }

                    } else {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("error"));
                        String msg = jsonObject1.optString("msg");
                        getmMvpView().showMessage(msg);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override public void accept(Throwable throwable) throws Exception {
                    getmMvpView().goError();
                }
            });
        getCompositeDisposable().add(disposable);
    }
}
