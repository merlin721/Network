package com.soyoung.component_base.widget.tablayout.utils;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import com.soyoung.component_base.widget.tablayout.widget.MsgView;


/**
 * 未读消息提示View,显示小红点或者带有数字的红点:
 * 数字一位,圆
 * 数字两位,圆角矩形,圆角是高度的一半
 * 数字超过两位,显示99+
 */
public class UnreadMsgUtils {
    public static void show(MsgView msgView, int num) {
        if (msgView == null) {
            return;
        }
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) msgView.getLayoutParams();
        DisplayMetrics dm = msgView.getResources().getDisplayMetrics();
        msgView.setVisibility(View.VISIBLE);
        if (num <= 0) {//圆点,设置默认宽高
            msgView.setStrokeWidth(0);
            msgView.setText("");

            lp.width = (int) (7 * dm.density);
            lp.height = (int) (7 * dm.density);
            msgView.setLayoutParams(lp);
        } else {
            lp.height = (int) (17 * dm.density);
            if (num > 0 && num < 10) {//圆
                lp.width = (int) (17 * dm.density);
                msgView.setText(num + "");
            } else if (num > 9 && num < 100) {//圆角矩形,圆角是高度的一半,设置默认padding
                msgView.setIsWidthHeightEqual(false);
                lp.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                msgView.setPadding((int) (5 * dm.density), (int) (1 * dm.density),
                    (int) (5 * dm.density), (int) (2 * dm.density));
                msgView.setText(num + "");
            } else {//数字超过两位,显示99+
                msgView.setIsWidthHeightEqual(false);
                lp.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                msgView.setPadding((int) (5 * dm.density), (int) (1 * dm.density),
                    (int) (5 * dm.density), (int) (2 * dm.density));
                msgView.setText("99+");
            }
            msgView.setLayoutParams(lp);
        }
    }

    /**
     * 自定义红点大小
     * @param msgView
     * @param size
     */
    public static void showBigDot(MsgView msgView, int size){
        if (msgView == null) {
            return;
        }
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) msgView.getLayoutParams();
        DisplayMetrics dm = msgView.getResources().getDisplayMetrics();
        msgView.setVisibility(View.VISIBLE);
        msgView.setStrokeWidth(0);
        msgView.setText("");
        lp.width = (int) (size * dm.density);
        lp.height = (int) (size * dm.density);
        msgView.setLayoutParams(lp);

    }

    public static void show(MsgView msgView, String txt) {
        if (msgView == null) {
            return;
        }
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) msgView.getLayoutParams();
        DisplayMetrics dm = msgView.getResources().getDisplayMetrics();
        msgView.setVisibility(View.VISIBLE);
        lp.height = (int) (10 * dm.density);
        lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        msgView.setPadding((int) (2 * dm.density), (int) (2 * dm.density), (int) (2 * dm.density), (int) (2 * dm.density));
        msgView.setText(txt);
        msgView.setLayoutParams(lp);

    }

    public static void setSize(MsgView rtv, int size) {
        if (rtv == null) {
            return;
        }
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rtv.getLayoutParams();
        lp.width = size;
        lp.height = size;
        rtv.setLayoutParams(lp);
    }
}
