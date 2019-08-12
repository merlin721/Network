package com.merlin.time.sharegallery.gallerylist.presenter;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.merlin.time.network.AppNetWorkHelper;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListCallBackModel;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListBaseModel;
import com.merlin.time.sharegallery.gallerylist.view.GalleryListView;
import com.soyoung.component_base.mvpbase.BasePresenter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.json.JSONObject;

/**
 * @author zhouyang
 * @date 2018/12/22
 * @desc
 */
public class GalleryListPresenter extends BasePresenter<GalleryListView> {

    public void getData(String album_id, int page, int size) {
        Disposable disposable = AppNetWorkHelper.getInstance().getGalleryList(album_id, page, size)
            .compose(toMain())
            .subscribe(new Consumer<JSONObject>() {
                @Override public void accept(JSONObject jsonObject) throws Exception {
                    GalleryListBaseModel model =
                        new Gson().fromJson(jsonObject.toString(), GalleryListBaseModel.class);
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

    public void getCallBack(){
        Disposable disposable  = AppNetWorkHelper.getInstance().getUploadCallback()
                .compose(toMain())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        GalleryListCallBackModel model = new Gson().fromJson(jsonObject.toString(), GalleryListCallBackModel.class);
                        getmMvpView().setCallbackParams(model);
                    }
                });
        getCompositeDisposable().add(disposable);
    }
}
