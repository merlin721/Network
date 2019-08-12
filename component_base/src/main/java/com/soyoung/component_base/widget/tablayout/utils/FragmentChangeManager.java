package com.soyoung.component_base.widget.tablayout.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

public class FragmentChangeManager<T extends Fragment> {
    private FragmentManager mFragmentManager;
    /** Fragment切换数组 */
    private ArrayList<T> mFragments;
    /** 当前选中的Tab */
    private int mCurrentTab=-1;

    public FragmentChangeManager(FragmentManager fm, int containerViewId, ArrayList<T> fragments) {
        this.mFragmentManager = fm;
        this.mFragments = fragments;
        initFragments(containerViewId);
    }

    /** 初始化fragments */
    private void initFragments(int containerViewId) {
        for (Fragment fragment : mFragments) {
            mFragmentManager.beginTransaction().add(containerViewId, fragment).hide(fragment).commit();
        }
        setFragments(0);
    }
    /** 界面切换控制 */
    public void setFragments(int index) {
        if (index==mCurrentTab)return;

        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment fragment = mFragments.get(i);
            if (i == index) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commitAllowingStateLoss();
        }
        mCurrentTab = index;
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public T getCurrentFragment() {
        return mFragments.get(mCurrentTab);
    }

    public ArrayList<T> getFragments() {
        return mFragments;
    }
}