package com.soyoung.component_base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.soyoung.component_base.mvp.factory.PresenterMvpFactory;
import com.soyoung.component_base.mvp.factory.PresenterMvpFactoryImpl;
import com.soyoung.component_base.mvp.presenter.BaseMvpPresenter;
import com.soyoung.component_base.mvp.proxy.BaseMvpProxy;
import com.soyoung.component_base.mvp.proxy.PresenterProxyInterface;
import com.soyoung.component_base.mvp.view.BaseMvpView;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/2/27
 * description   : AbstractFragment
 */
public abstract class AbstractFragment <V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends Fragment implements PresenterProxyInterface<V, P> {
    private BaseMvpProxy<V, P> mvpProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V, P>createFactory(getClass()));
    private static final String FRAGMENT_KEY = "fragment_key";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mvpProxy.onContentChanged((V) this);
        if (null!=savedInstanceState){
            mvpProxy.onRestoreInstanceState(savedInstanceState.getBundle(FRAGMENT_KEY));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(FRAGMENT_KEY,mvpProxy.onSaveInstanceState());
    }

    @Override
    public void onDestroyView() {
        mvpProxy.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) throws IllegalAccessError {
        mvpProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mvpProxy.getPresenterFactory();
    }

    @Override
    public P getMvpPresenter() {
        return mvpProxy.getMvpPresenter();
    }
}
