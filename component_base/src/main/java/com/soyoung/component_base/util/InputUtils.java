package com.soyoung.component_base.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 键盘的工具类
 */

public class InputUtils {

    public static void hideInput(Context context, EditText edit) {
        try {
            if (context != null) {
                if (isSoftShowing(((Activity) context))) {
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    View cf = ((Activity) context).getCurrentFocus();
                    if (inputMethodManager != null && cf != null && cf.getWindowToken() != null) {
                        inputMethodManager.hideSoftInputFromWindow(((Activity) context)
                                        .getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                    try {
                        // 隐藏输入法
//                        edit.setInputType(0);
                        inputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                    } catch (Exception e) {
                    }
                }
            }


//            // 隐藏输入法
//            edit.setInputType(0);
//            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public static void hideInput(Activity context) {
        if (context.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (context.getCurrentFocus() != null) {
                InputMethodManager manager = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static void showInput(Context context, EditText edit) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            // 显示输入法
            imm.showSoftInput(edit, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
        }
    }

    //软键盘是否显示  不准确，不要使用，用isSoftShowing
//    public static boolean inputIsShow(Context context) {
//        try {
//            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//            return imm.isActive();
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public static boolean isSoftShowing(Activity activity) {
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return screenHeight - rect.bottom - getSoftButtonsBarHeight(activity)!= 0;
    }

    /**
     * 底部虚拟按键栏的高度
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int getSoftButtonsBarHeight(Activity mActivity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }



    // 隐藏输入法
    public static void hideInput(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (isSoftShowing(((Activity)context))) {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
        }
    }
}
