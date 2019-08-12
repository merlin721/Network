package com.soyoung.component_base.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/10/31
 * description   : TitleBar TextView
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    private DrawableListener drawableListener;

    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;
    private boolean isEnable = true;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setDrawableListener(DrawableListener listener) {
        this.drawableListener = listener;
    }

    public void setEnabledClick(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public interface DrawableListener {
        void onDrawableLeftClick(View view);

        void onDrawableRightClick(View view);

        void onRightListener(View view);
    }

    public abstract static class OnDrawableListener implements DrawableListener {
        @Override
        public void onDrawableLeftClick(View view) {

        }

        @Override
        public void onDrawableRightClick(View view) {

        }

        @Override
        public void onRightListener(View view) {

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (drawableListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
                        if (isEnable) {
                            drawableListener.onDrawableRightClick(this);
                            return true;
                        }
                    }
                }
                if (drawableListener != null) {
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {
                        if (isEnable) {
                            drawableListener.onDrawableLeftClick(this);
                            return true;
                        }
                    }
                }
                if (drawableListener != null) {
                    if (isEnable) {
                        drawableListener.onRightListener(this);
                        return true;
                    }
                }
                break;
        }
        return true;
    }
}