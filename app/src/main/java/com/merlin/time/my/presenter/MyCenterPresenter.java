package com.merlin.time.my.presenter;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.merlin.time.my.model.MyCenterBaseModel;
import com.merlin.time.my.model.MyCenterModel;
import com.merlin.time.my.model.MyCenterSignBaseBean;
import com.merlin.time.my.view.MyCenterView;
import com.merlin.time.network.AppNetWorkHelper;
import com.merlin.time.sharegallery.model.ShareGalleryBaseModel;
import com.merlin.time.sharegallery.view.ShareGalleryView;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.mvpbase.BasePresenter;
import com.soyoung.component_base.util.LogUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.json.JSONObject;

/**
 * @author zhouyang
 * @date 2018/11/18
 * @desc 我的界面
 */
public class MyCenterPresenter extends BasePresenter<MyCenterView> {

  public void getData(int page) {
    Disposable disposable = AppNetWorkHelper.getInstance()
        .getShareList(page)
        .compose(toMain())
        .subscribe(new Consumer<JSONObject>() {
          @Override public void accept(JSONObject jsonObject) throws Exception {
            MyCenterBaseModel model =
                new Gson().fromJson(jsonObject.toString(), MyCenterBaseModel.class);
            if (!TextUtils.isEmpty(model.ret) & "1".equals(model.ret)) {
              getmMvpView().showData(model.getData());
            } else {
              getmMvpView().showMessage(model.ret);
            }
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            throwable.printStackTrace();
          }
        });
    getCompositeDisposable().add(disposable);
  }

  public void sign() {
    Disposable disposable = AppNetWorkHelper.getInstance()
        .sign()
        .compose(toMain())
        .subscribe(new Consumer<JSONObject>() {
          @Override public void accept(JSONObject jsonObject) throws Exception {
            LogUtils.e(jsonObject.toString());
            MyCenterSignBaseBean model =
                new Gson().fromJson(jsonObject.toString(), MyCenterSignBaseBean.class);
            LogUtils.e(jsonObject.toString());
            if (!TextUtils.isEmpty(model.ret) & "1".equals(model.ret)) {
              getmMvpView().signSuccess(model.getData());
            } else if ("0".equals(model.ret)){
              getmMvpView().signedSuccess();
            }
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            throwable.printStackTrace();
          }
        });
    getCompositeDisposable().add(disposable);
  }
}
