package com.soyoung.component_base.mvp.proxy;

import android.os.Bundle;

import com.soyoung.component_base.mvp.factory.PresenterMvpFactory;
import com.soyoung.component_base.mvp.presenter.BaseMvpPresenter;
import com.soyoung.component_base.mvp.view.BaseMvpView;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/2/27
 * description   : 代理实现类，用来管理Presenter的生命周期，还有和view之间的关联
 */

public class BaseMvpProxy<V extends BaseMvpView,P extends BaseMvpPresenter<V>> implements PresenterProxyInterface<V,P> {
    private static final String BUNDLE_KEY = "BUNDLE_KEY";
    private PresenterMvpFactory<V,P> mvpFactory;
    private P mPresenter;
    private Bundle mBundle;
    private boolean mIsAttachView;

    public BaseMvpProxy(PresenterMvpFactory<V, P> mvpFactory) {
        this.mvpFactory = mvpFactory;
    }
    /**
     * 设置Presenter的工厂类,这个方法只能在创建Presenter之前调用,也就是调用getMvpPresenter()之前，如果Presenter已经创建则不能再修改
     * @param presenterFactory PresenterFactory类型
     */
    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) throws IllegalAccessError {
        if (mPresenter!=null){
            throw new IllegalArgumentException("这个方法只能在getMvpPresenter()之前调用,如果Presenter已经创建则不能再修改");
        }
        this.mvpFactory=presenterFactory;
    }
    /**
     * 获取Presenter的工厂类
     * @return PresenterMvpFactory类型
     */
    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mvpFactory;
    }

    @Override
    public P getMvpPresenter() {
        if (mvpFactory!=null){
            if (mPresenter==null){
                mPresenter=mvpFactory.createMvpPresenter();
                mPresenter.onCreatePresenter(mBundle==null?null:mBundle.getBundle(BUNDLE_KEY));
            }
        }
        return mPresenter;
    }
    /**
     * 绑定Presenter和view
     * @param mvpView
     */
    public void onContentChanged(V mvpView){
        getMvpPresenter();
        if (mPresenter!=null&&!mIsAttachView){
            mPresenter.onAttachMvpView(mvpView);
            mIsAttachView=true;
        }
    }
    /**
     * 销毁presenter持有的View
     */
    public void onDetachMvpView(){
        if (mPresenter!=null&&mIsAttachView){
            mPresenter.onDetachMvpView();
            mIsAttachView=false;
        }
    }
    /**
     * 销毁presenter
     */
    public void onDestroy(){
        if (mPresenter!=null){
            onDetachMvpView();
            mPresenter.onDestroyPresenter();
            mPresenter=null;
        }
    }
    /**
     * 意外销毁的时候调用
     * @return Bundle，存入回调给Presenter的Bundle和当前Presenter的id
     */
    public Bundle onSaveInstanceState(){
        Bundle bundle=new Bundle();
        getMvpPresenter();
        if (mPresenter!=null){
            Bundle presenterBundle=new Bundle();
            mPresenter.onSaveInstanceState(presenterBundle);
            bundle.putBundle(BUNDLE_KEY,presenterBundle);
        }
        return bundle;
    }
    /**
     * 意外关闭恢复Presenter
     * @param savedInstanceState 意外关闭时存储的Bundler
     */
    public void onRestoreInstanceState(Bundle savedInstanceState){
        mBundle=savedInstanceState;
    }
}
