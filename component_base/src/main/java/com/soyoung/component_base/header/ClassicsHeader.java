package com.soyoung.component_base.header;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.soyoung.component_base.R;
import com.soyoung.component_base.image.ImageWorker;


/**
 * 经典下拉头部
 * Created by SCWANG on 2017/5/28.
 */
public class ClassicsHeader extends FrameLayout implements RefreshHeader {
    private ImageView imageView;
    @DrawableRes
    protected int mGifFrameResId = R.drawable.load_blue;
    @DrawableRes
    protected int mGifLoadingResId = R.drawable.loading_blue;
    private SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;

    public void setmSpinnerStyle(SpinnerStyle mSpinnerStyle) {
        this.mSpinnerStyle = mSpinnerStyle;
    }

    public ClassicsHeader(Context context) {
        this(context, null);
    }

    public ClassicsHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        int h = DensityUtil.dp2px(40);
        LayoutParams relativeLayoutParam = new LayoutParams(h, h, Gravity.CENTER);
        addView(imageView, relativeLayoutParam);
        setMinimumHeight(DensityUtil.dp2px(60));
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
    }


    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {
        Glide.with(getContext()).asGif().load(mGifLoadingResId).into(imageView);
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        return 500;//延迟500毫秒之后再弹回
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void setPrimaryColors(int... colors) {
        if (colors.length > 1) {
            setBackgroundColor(colors[0]);
        } else if (colors.length > 0) {
            setBackgroundColor(colors[0]);
        }
    }

    @NonNull
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        if (newState == RefreshState.None) {
            imageView.setImageResource(mGifFrameResId);
        } else {
            ImageWorker.imageLoaderGif(getContext(),mGifLoadingResId,imageView);
        }
    }


    public void setGifDrawable(Boolean isBlue) {
        if (isBlue) {
            mGifFrameResId = R.drawable.load_blue;
            mGifLoadingResId = R.drawable.loading_blue;
        } else {
            mGifFrameResId = R.drawable.load_white;
            mGifLoadingResId = R.drawable.loading_white;
        }
    }
}
