package com.soyoung.component_base.state_page;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.loadsir.callback.Callback;
import com.soyoung.component_base.R;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/11/17
 * description   : 没有数据 状态页
 */

public class EmptyCallback extends Callback {


    public EmptyCallback() {
    }

    @Override
    protected int onCreateView() {
        return R.layout.load_failed_view;
    }

    @Override
    protected void onViewCreate(Context context, final View view) {
        super.onViewCreate(context, view);

        TextView textView = view.findViewById(R.id.loadfailtext);
        textView.setText("暂无数据!");


        TextView refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReloadListener.onReload(view);
            }
        });
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }


}
