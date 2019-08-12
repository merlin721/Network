package com.soyoung.component_base.state_page;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.soyoung.component_base.R;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/11/17
 * description   : 加载失败页面
 */

public class LoadFailCallback extends Callback {

    public LoadFailCallback() {

    }

    @Override
    protected int onCreateView() {
        return R.layout.load_failed_view;
    }

}
