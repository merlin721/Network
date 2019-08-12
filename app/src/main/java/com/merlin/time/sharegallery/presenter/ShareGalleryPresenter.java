package com.merlin.time.sharegallery.presenter;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.merlin.time.network.AppNetWorkHelper;
import com.merlin.time.sharegallery.model.ShareGalleryBaseModel;
import com.merlin.time.sharegallery.view.ShareGalleryView;
import com.soyoung.component_base.mvpbase.BasePresenter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.json.JSONObject;

/**
 * @author zhouyang
 * @date 2018/11/18
 * @desc
 */
public class ShareGalleryPresenter extends BasePresenter<ShareGalleryView> {

    public void getData(int page) {
        Disposable disposable = AppNetWorkHelper.getInstance()
            .getShareList(page)
            .compose(toMain())
            .subscribe(new Consumer<JSONObject>() {
                @Override public void accept(JSONObject jsonObject) throws Exception {
                    ShareGalleryBaseModel model =
                        new Gson().fromJson(jsonObject.toString(), ShareGalleryBaseModel.class);
                    if (!TextUtils.isEmpty(model.ret) & "1".equals(model.ret)) {
                        getmMvpView().showData(model.getData());
                    }else {
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
}
