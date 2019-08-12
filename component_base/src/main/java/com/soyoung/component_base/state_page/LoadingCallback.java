package com.soyoung.component_base.state_page;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.loadsir.callback.Callback;
import com.soyoung.component_base.R;
import com.soyoung.component_base.widget.LoadingView;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/11/17
 * description   : 加载中页面
 */

public class LoadingCallback extends Callback {

    private int drawableRes;

    public LoadingCallback() {

    }

    public LoadingCallback(@DrawableRes int resource) {
        drawableRes = resource;
    }

    @Override
    protected int onCreateView() {
        return  R.layout.loading_callback_view ;
    }

    @Override
    public void onAttach(Context context, View view) {
        ImageView loading_image = view.findViewById(R.id.loading_image);
        if (0!=drawableRes){
            loading_image.setBackgroundResource(drawableRes);
        }
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
