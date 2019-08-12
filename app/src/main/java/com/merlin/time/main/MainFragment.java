package com.merlin.time.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.merlin.time.R;
import com.merlin.time.main.adapter.HomePagerAdapter;
import com.soyoung.component_base.mvpbase.BaseFragment;
import com.soyoung.component_base.widget.tablayout.SlidingTabLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyang
 * @date 2018/11/11
 * @desc
 */
public class MainFragment extends BaseFragment {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ArrayList<BaseFragment> fragments;
    private HomePagerAdapter adapter;

    @Override
    protected int setLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    protected void initView() {
        slidingTabLayout = findViewById(R.id.home_tabLayout);
        viewPager = findViewById(R.id.home_view_pager);
        fragments = new ArrayList<>();

        fragments.add(HomeChildFragment.newInstance("new_upload"));
        fragments.add(HomeChildFragment.newInstance("like"));
        fragments.add(HomeChildFragment.newInstance("video"));
        adapter = new HomePagerAdapter(getChildFragmentManager());
        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(0);
        slidingTabLayout.setCurrentTab(0);

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        //统计页面，"MainScreen"为页面名称，可自定义
        MobclickAgent.onPageStart(MainFragment.class.getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(MainFragment.class.getSimpleName());
    }
}
