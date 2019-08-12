package com.soyoung.component_base.mvp.factory;


import com.soyoung.component_base.mvp.presenter.BaseMvpPresenter;
import com.soyoung.component_base.mvp.view.BaseMvpView;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/2/27
 * description   : Presenter工厂类
 */

public class PresenterMvpFactoryImpl<V extends BaseMvpView, P extends BaseMvpPresenter<V>> implements PresenterMvpFactory<V, P> {
    private final Class<P> mPresenterClass;

    public static <V extends BaseMvpView, P extends BaseMvpPresenter<V>> PresenterMvpFactoryImpl createFactory(Class<?> viewClazz) {
        CreatePresenter annotation = viewClazz.getAnnotation(CreatePresenter.class);
        Class<P> aClass = null;
        if (annotation != null) {
            aClass = (Class<P>) annotation.value();
        }
        return aClass == null ? null : new PresenterMvpFactoryImpl<V, P>(aClass);
    }

    public PresenterMvpFactoryImpl(Class<P> mPresenterClass) {
        this.mPresenterClass = mPresenterClass;
    }

    @Override
    public P createMvpPresenter() {
        try {
            return mPresenterClass.newInstance();
        } catch (Exception e) {
           throw new RuntimeException("Presenter创建失败!,检查是否声明了@CreatePresenter(xx.class)注解");
        }
    }
}
