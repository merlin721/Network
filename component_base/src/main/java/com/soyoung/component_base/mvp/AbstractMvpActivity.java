package com.soyoung.component_base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
 * description   : mvp Activity
 */

public abstract class AbstractMvpActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends AppCompatActivity implements PresenterProxyInterface<V, P> {
    private BaseMvpProxy<V, P> mvpProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V, P>createFactory(getClass()));
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            mvpProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
    }
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mvpProxy.onContentChanged((V) this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY,mvpProxy.onSaveInstanceState());
    }

    @Override
    protected void onDestroy() {
        mvpProxy.onDestroy();
        super.onDestroy();
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
