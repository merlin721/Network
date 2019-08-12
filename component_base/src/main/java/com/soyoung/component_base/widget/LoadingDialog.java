package com.soyoung.component_base.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soyoung.component_base.R;

/**
 * Created by 56417 on 2017/10/25.
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private TextView loadTv;
        private LinearLayout loading_root_view;
        private int backgroundColor= Color.TRANSPARENT;

        private String loadText;
        private boolean canCancelTouchOutside = false;//默认屏幕外点击不可取消
        private boolean canCancelAble = true;//默认点击back键可以取消


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setCanCancelAble(boolean canCancelAble) {
            this.canCancelAble = canCancelAble;
            return this;
        }

        public Builder setCanCancelTouchOutside(boolean canCancelTouchOutside) {
            this.canCancelTouchOutside = canCancelTouchOutside;
            return this;
        }

        public Builder setLoadText(String loadText) {
            this.loadText = loadText;
            return this;
        }
        public Builder setBackGround(int color){
            backgroundColor=color;
            return this;
        }

        public LoadingDialog create() {
            LoadingDialog dialog = new LoadingDialog(context, R.style.gif_dialog);
            dialog.setContentView(R.layout.loading_dialog);
            loadTv = ((TextView) dialog.findViewById(R.id.tv_text));
            loading_root_view = ((LinearLayout) dialog.findViewById(R.id.loading_root_view));
            if (!TextUtils.isEmpty(loadText)) {
                loadTv.setText(loadText);
            }else {
                loadTv.setVisibility(View.GONE);
            }
            loading_root_view.setBackgroundColor(backgroundColor);
            dialog.setCanceledOnTouchOutside(canCancelTouchOutside);
            dialog.setCancelable(canCancelAble);

            return dialog;
        }
    }


}
