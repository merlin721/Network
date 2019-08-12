package com.soyoung.component_base.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by guohuanwen on 2015/10/6.
 */
public class LoadingView extends View {
    private String TAG = "FourCirlceRotate";
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;
    private Paint paint5;
    private ValueAnimator valueAnimator;
    private int delay = 500;
    private boolean init = false;
    private float R = 0;
    private boolean stop = false;

    private float where = 0;
    private boolean isStart = false;
    private int show = 4;

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint1 = new Paint();
        paint2 = new Paint();
        paint3 = new Paint();
        paint4 = new Paint();
        paint5 = new Paint();
        paint1.setColor(Color.parseColor("#ff31CAC4"));
        paint2.setColor(Color.parseColor("#ff53E2C0"));
        paint3.setColor(Color.parseColor("#ff53E2C0"));
        paint4.setColor(Color.parseColor("#ff53E2C0"));
        paint5.setColor(Color.parseColor("#ff53E2C0"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!init) {
            startAnimation();

        }
        //显示固定的圆，逐个出现
        if (show <= 4) {
            canvas.drawCircle(R, R, R, paint2);
        }
        if (show <= 3) {
            canvas.drawCircle(getWidth() - R, R, R, paint3);
        }
        if (show <= 2) {
            canvas.drawCircle(getWidth() - R, getHeight() - R, R, paint4);
        }
        if (show <= 1) {
            canvas.drawCircle(R, getHeight() - R, R, paint5);
        }
        //移动
        if (where < 1 && where > 0) {
            canvas.drawCircle(R + (getWidth() - 2 * R) * where, R, R, paint1);
            show = 4;
        }
        if (where < 2 && where > 1) {
            canvas.drawCircle(getHeight() - R, R + (getHeight() - 2 * R) * (where - 1), R, paint1);
            show = 3;
        }
        if (where < 3 && where > 2) {
            canvas.drawCircle(getWidth() - R - (getWidth() - 2 * R) * (where - 2), getHeight() - R, R, paint1);
            show = 2;
        }
        if (where < 4 && where > 3) {
            canvas.drawCircle(R, getHeight() - R - (getHeight() - 2 * R) * (where - 3), R, paint1);
            show = 1;
        }
        if (isStart) {
            where = (float) valueAnimator.getAnimatedValue();
        }
        if (valueAnimator.isRunning()) {
            isStart = true;
            invalidate();
        }


    }


    public void restart(){
        stop = false;
        init = false;
        postInvalidate();
    }


    public void startAnimation() {
        if (valueAnimator == null) {
            valueAnimator = getValueAnimator();
        }
        valueAnimator.start();

        R = getWidth() / 4.2f;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!stop) {
                    startAnimation();
                    invalidate();
                }
            }
        }, valueAnimator.getDuration());
        init = true;
    }

    public void stop() {
        this.stop = true;
    }

    private ValueAnimator getValueAnimator() {
//        Log.i(TAG, "getValueAnimator ");
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 4f);
        valueAnimator.setDuration(1600);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
//        invalidate();
        return valueAnimator;
    }


}
