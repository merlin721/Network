package com.soyoung.component_base.mvp.factory;


import com.soyoung.component_base.mvp.presenter.BaseMvpPresenter;
import com.soyoung.component_base.mvp.view.BaseMvpView;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/2/27
 * description   : 创建Presenter 工厂
 */

public interface PresenterMvpFactory<V extends BaseMvpView,P extends BaseMvpPresenter<V>>{
    P createMvpPresenter();
}
