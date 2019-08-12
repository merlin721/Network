package com.merlin.time.main.presenter;

import com.google.gson.Gson;
import com.merlin.time.main.HomeChildView;
import com.merlin.time.main.model.HomeChildBaseModel;
import com.merlin.time.main.model.HomeCommonBaseModel;
import com.merlin.time.main.model.HomeLikeBaseModel;
import com.merlin.time.network.AppNetWorkHelper;
import com.soyoung.component_base.mvpbase.BasePresenter;
import com.soyoung.component_base.util.LogUtils;

import org.json.JSONObject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author merlin720
 * @date 2019/2/23
 * @mail zy44638@gmail.com
 * @description
 */
public class HomeChildPresenter extends BasePresenter<HomeChildView> {
    /**
     * tab:new_upload 最新上传
     * tab:new_photograph 最新拍照
     * tab:video 最新视频
     * tab:like 最新点赞
     *
     * @param page
     * @param tab
     */
    public void getTabData(int page, String tab) {
        Disposable disposable = AppNetWorkHelper.getInstance().getTabData(page, tab)
                .compose(toMain())
                .subscribe(jsonObject -> {
                    LogUtils.d(jsonObject.toString());
                    HomeChildBaseModel model = new Gson().fromJson(jsonObject.toString(), HomeChildBaseModel.class);
                    if ("1".equals(model.ret)) {
                        getmMvpView().showData(model.getData());
                    } else {
                        getmMvpView().showMessage(model.error.getMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        getmMvpView().showMessage("哎呀抛异常了");
                    }
                });
        getCompositeDisposable().add(disposable);
    }

    public void doLike(String id,final int position) {
        Disposable disposable = AppNetWorkHelper.getInstance().homeLike(id)
                .compose(toMain())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        HomeLikeBaseModel model = new Gson().fromJson(jsonObject.toString(), HomeLikeBaseModel.class);
                        if ("1".equals(model.ret)) {
                            getmMvpView().doLike(model.getData(), position);
                        } else {
                            getmMvpView().showMessage(model.error.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        getmMvpView().showMessage("哎呀抛异常了");
                    }
                });
        getCompositeDisposable().add(disposable);
    }

    public void doDiscuss(String id,String content,int posotion){
        Disposable disposable = AppNetWorkHelper.getInstance()
                .homeDiscuss(id,content)
                .compose(toMain())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        HomeCommonBaseModel model = new Gson().fromJson(jsonObject.toString(), HomeCommonBaseModel.class);
                        if ("1".equals(model.ret)) {
                            getmMvpView().doCommon(model.getData(),posotion);
                        } else {
                            getmMvpView().showMessage(model.error.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        getmMvpView().showMessage("哎呀抛异常了");
                    }
                });
        getCompositeDisposable().add(disposable);
    }

}
