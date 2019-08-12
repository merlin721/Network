package com.merlin.time.mygallery;

import com.merlin.time.R;
import com.soyoung.component_base.mvpbase.BaseFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * @author zhouyang
 * @date 2018/11/11
 * @desc 我的相册
 */
public class MyGalleryFragment extends BaseFragment {

    @Override protected int setLayoutId() {
        return R.layout.my_gallery_fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        //统计页面，"MainScreen"为页面名称，可自定义
        MobclickAgent.onPageStart(MyGalleryFragment.class.getSimpleName());
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(MyGalleryFragment.class.getSimpleName());
    }
}
