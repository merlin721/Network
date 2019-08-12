package com.merlin.time.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.merlin.time.main.HomeChildFragment;
import com.soyoung.component_base.mvpbase.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author merlin720
 * @date 2019/2/23
 * @mail zy44638@gmail.com
 * @description
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<BaseFragment> fragments;
    private List<String> names;
    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        names  = new ArrayList<>();
        names.add("最新动态");
        names.add("我点赞的");
        names.add("视频");
    }

    public void setFragments(ArrayList<BaseFragment> fragments) {
        this.fragments = fragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (names == null || names.size() == 0)
            return null;
        return names.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
