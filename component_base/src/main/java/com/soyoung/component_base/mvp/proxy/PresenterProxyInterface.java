package com.soyoung.component_base.mvp.proxy;


import com.soyoung.component_base.mvp.factory.PresenterMvpFactory;
import com.soyoung.component_base.mvp.presenter.BaseMvpPresenter;
import com.soyoung.component_base.mvp.view.BaseMvpView;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/2/27
 * description   : 代理接口
 */
public interface PresenterProxyInterface<V extends BaseMvpView, P extends BaseMvpPresenter<V>> {
    /**
     * 设置创建Presenter的工厂
     * */
    void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) throws IllegalAccessError;
    /**
     * 获取工厂
     * */
    PresenterMvpFactory<V, P> getPresenterFactory();
    /**
     * 获取创建的Presenter
     * */
    P getMvpPresenter();
}
