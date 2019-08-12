package com.soyoung.component_base.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.soyoung.component_base.R;

public class ToastUtils {

    private static Toast toast;
    private static Toast mToast;


    public static void showToast(Context context, final String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        try {

            if (toast != null) {
                toast.cancel();
                toast = null;
            }
            toast = getCustomToast(context, message);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLtoast(Context context, int resourceId) {
        showLtoast(context, context.getString(resourceId));
    }

    public static void showLtoast(Context context, final String message) {
        try {
            if (TextUtils.isEmpty(message)) {
                return;
            }
            if (toast != null) {
                toast.cancel();
                toast = null;
            }
            toast = getCustomToast(context, message);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, int resourceId) {
        showToast(context, context.getString(resourceId));
    }

    public static void showBottomToast(Context context, String message) {
        showBottomToast(context,Toast.LENGTH_SHORT,message);
    }

    public static void showBottomToast(Context context, int toastLength, String message) {
        try {
            if (TextUtils.isEmpty(message)) {
                return;
            }
            toast = getBottomCustomToast(context, message, 500);
            toast.setDuration(toastLength);
            toast.show();
        } catch (Exception e) {
        }
    }


    public static Toast getCustomToast(Context context, String resId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        Toast t = new Toast(context);
        View view = inflater.inflate(R.layout.float_dialog, null);
        TextView text = (TextView) view.findViewById(R.id.dialog_text);
        text.setText(resId);
        t.setView(view);
        t.setGravity(Gravity.CENTER, 0, 0);
        return t;
    }

    public static Toast getBottomCustomToast(Context context, String resId, int yoffset) {
        LayoutInflater inflater = LayoutInflater.from(context);
        Toast t = new Toast(context);
        View view = inflater.inflate(R.layout.float_dialog, null);
        TextView text = (TextView) view.findViewById(R.id.dialog_text);
        text.setText(resId);
        t.setView(view);
        t.setGravity(Gravity.CENTER, 0, yoffset);
        return t;
    }


    public static void showMToast(Context context, int resId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (mToast == null) {
            mToast = new Toast(context);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        View view = inflater.inflate(R.layout.float_dialog, null);
        TextView text = (TextView) view.findViewById(R.id.dialog_text);
        text.setText(resId);
        mToast.setView(view);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public static void showMToast(Context context, String str) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (mToast == null) {
            mToast = new Toast(context);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        View view = inflater.inflate(R.layout.float_dialog, null);
        TextView text = (TextView) view.findViewById(R.id.dialog_text);
        text.setText(str);
        mToast.setView(view);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public static void showIconToast(Context context, String str) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (mToast == null) {
            mToast = new Toast(context);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        View view = inflater.inflate(R.layout.float_icon_dialog, null);
        TextView text =  view.findViewById(R.id.dialog_text);
        text.setText(str);
        mToast.setView(view);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }
    public static void cancleToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
