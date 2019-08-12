package com.merlin.time.sharegallery.galleryitem.presenter;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.merlin.time.network.AppNetWorkHelper;
import com.merlin.time.sharegallery.galleryitem.model.GalleryItemBaseBean;
import com.merlin.time.sharegallery.galleryitem.view.GalleryItemView;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListBaseModel;
import com.soyoung.component_base.mvpbase.BasePresenter;
import com.soyoung.component_base.network.ErrorConsumer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.json.JSONObject;

/**
 * @author zhouyang
 * @date 2018/12/30
 * @desc
 */
public class GalleryItemPresenter extends BasePresenter<GalleryItemView> {

    public void getData(String id,int page){
        Disposable disposable = AppNetWorkHelper
            .getInstance()
            .getCommentList(id,page,20)
            .compose(toMain())
            .subscribe(new Consumer<JSONObject>() {
                @Override public void accept(JSONObject jsonObject) throws Exception {
                    GalleryItemBaseBean model =
                        new Gson().fromJson(jsonObject.toString(), GalleryItemBaseBean.class);
                    if (!TextUtils.isEmpty(model.ret) & "1".equals(model.ret)) {
                        getmMvpView().showData(model.getData());
                    }else {
                        getmMvpView().showMessage(model.ret);
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
