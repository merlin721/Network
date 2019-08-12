package com.soyoung.component_base.network;

import com.soyoung.component_base.mvpbase.BasePresenter;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/9/27
 * description   : 网络请求 结果处理类
 */

public abstract class CompleteConsumer implements Consumer<JSONObject> {
    private  BasePresenter presenter;

    public CompleteConsumer() {
    }

    public CompleteConsumer(BasePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void accept(JSONObject jsonObject) throws Exception {
        if (null!=presenter){
            presenter.getmMvpView().hideLoadingDialog();
        }
        String errorCode = jsonObject.optString("errorCode");
        String errorMsg = jsonObject.optString("errorMsg");
        JSONObject responseData=null;
        if ("0000".equals(errorCode)){
            responseData = jsonObject.optJSONObject("responseData");
        }
        onResponseData(responseData,errorMsg);
    }
    public abstract void onResponseData(JSONObject responseData,String errorMsg);
}
