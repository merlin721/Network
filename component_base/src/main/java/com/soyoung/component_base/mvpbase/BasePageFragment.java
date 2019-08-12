package com.soyoung.component_base.mvpbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * author : wangwenwang
 * e-mail : wangwenwang@soyoung.com
 * date   : 2018/8/7
 * description   : 懒加载fragment
 */
public abstract class BasePageFragment extends BaseFragment {
    private boolean isViewCreated;//视图是否已经创建
    private boolean isUiVisible;//该fragment是否对用户可见

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUiVisible = true;
            lazyLoad();
        } else {
            isUiVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,
        // 并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUiVisible) {
            onRefreshData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUiVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isUiVisible = false;
    }
}
