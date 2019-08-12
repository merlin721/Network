package com.soyoung.component_base.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.soyoung.component_base.mvp.view.BaseMvpView;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/2/27
 * description   : BaseMvpPresenter
 */
public abstract class BaseMvpPresenter<V extends BaseMvpView> {
    private V mMvpView;

    public void onCreatePresenter(@Nullable Bundle savedState){

    }
    /**
     * 绑定
     * */
    public void onAttachMvpView(V view){
        this.mMvpView=view;
    }
    /**
     * 解绑
     * */
    public void onDetachMvpView(){
        mMvpView=null;
    }
    /**
     * Presenter销毁时调用
     * */
    public void onDestroyPresenter(){

    }
    /**
     在Presenter意外销毁的时候被调用，它的调用时机和Activity、Fragment、View中的onSaveInstanceState
     * 时机相同
     * @param outState
     * */
    public void onSaveInstanceState(Bundle outState){

    }
    /**
     * 获取V层接口
     * */
    public V getmMvpView(){
        return mMvpView;
    }
}

