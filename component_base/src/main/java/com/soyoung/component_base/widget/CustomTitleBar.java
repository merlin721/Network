package com.soyoung.component_base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soyoung.component_base.R;
import com.soyoung.component_base.util.SizeUtils;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/10/31
 * description   : 通用TitleBar
 */

public class CustomTitleBar extends RelativeLayout implements View.OnClickListener {
    private String leftTitle;
    private String middleTitle;
    private String rightTitle;

    private int leftTextColor;
    private int middleTextColor;
    private int rightTextColor;

    private float leftTextSize;
    private float rightTextSize;
    private float middleTextSize;

    private TextView tvLeft;
    private TextView tvMiddle;
    private CustomTextView tvRight;
    private View title_line;

    private int leftImage;
    private int rightImage;
    private int rightImage2;

    private TitleClickListener listener;
    private RelativeLayout rootView;

    public CustomTitleBar(Context context) {
        this(context, null);
    }

    public CustomTitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array =
            context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar, defStyleAttr, 0);
        initView(array);
        array.recycle();
    }

    private void initView(TypedArray array) {

        LayoutInflater.from(getContext()).inflate(R.layout.base_title_layout, this);
        rootView = (RelativeLayout) findViewById(R.id.title_root_view);
        tvLeft = (TextView) findViewById(R.id.tvLeftTitle);
        tvLeft.setOnClickListener(this);
        tvMiddle = (TextView) findViewById(R.id.tvMiddleTitle);
        tvMiddle.setOnClickListener(this);
        tvRight = (CustomTextView) findViewById(R.id.tvRightTitle);
        title_line = findViewById(R.id.title_line);

        leftTitle = array.getString(R.styleable.CustomTitleBar_leftTitle);
        middleTitle = array.getString(R.styleable.CustomTitleBar_middleTitle);
        rightTitle = array.getString(R.styleable.CustomTitleBar_rightTitle);

        leftTextColor = array.getColor(R.styleable.CustomTitleBar_leftTextColor, Color.GRAY);
        middleTextColor =
            array.getColor(R.styleable.CustomTitleBar_middleTextColor, Color.TRANSPARENT);
        rightTextColor = array.getColor(R.styleable.CustomTitleBar_rightTextColor, Color.BLACK);

        leftImage = array.getResourceId(R.styleable.CustomTitleBar_leftImage, 0);
        rightImage = array.getResourceId(R.styleable.CustomTitleBar_rightImage, 0);
        rightImage2 = array.getResourceId(R.styleable.CustomTitleBar_rightImage2, 0);

        leftTextSize =
            array.getDimension(R.styleable.CustomTitleBar_leftTextSize, SizeUtils.dp2px(getContext(),15));
        rightTextSize =
            array.getDimension(R.styleable.CustomTitleBar_rightTextSize, SizeUtils.dp2px(getContext(),17));
        middleTextSize =
            array.getDimension(R.styleable.CustomTitleBar_middleTextSize, SizeUtils.dp2px(getContext(),15));

        if (leftImage > 0) {
            setLeftImage(leftImage);
        } else {
            setLeftTitle(leftTitle);
        }

        if (rightImage > 0) {
            setRightImage(rightImage);
        } else {
            setRightTitle(rightTitle);
        }

        if (rightImage > 0 && rightImage2 > 0) {
            setRightImage(rightImage, rightImage2);
        }

        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
        tvMiddle.setTextSize(TypedValue.COMPLEX_UNIT_PX, middleTextSize);

        setMiddleTitle(middleTitle);
        setLeftTextColor(leftTextColor);
        setMiddleTextColor(middleTextColor);
        setRightTextColor(rightTextColor);

        tvRight.setDrawableListener(new CustomTextView.DrawableListener() {
            @Override
            public void onRightListener(View view) {
                if (listener != null) {
                    listener.onRightClick();
                }
            }

            @Override
            public void onDrawableRightClick(View view) {
                if (listener != null) {
                    listener.onRightButtonRightClick();
                }
            }

            @Override
            public void onDrawableLeftClick(View view) {
                if (listener != null) {
                    listener.onRightButtonLeftClick();
                }
            }
        });
    }

    /**
     * @param size 单位sp
     */
    public void setLeftTextSize(float size) {
        tvLeft.setTextSize(size);
    }

    /**
     * @param size 单位sp
     */
    public void setMiddleTextSize(float size) {
        tvMiddle.setTextSize(size);
    }

    /**
     * @param size 单位sp
     */
    public void setRightTextSize(float size) {
        tvRight.setTextSize(size);
    }

    public void setLeftTextColor(int color) {
        tvLeft.setTextColor(color);
    }

    public void setMiddleTextColor(int color) {
        tvMiddle.setTextColor(color);
    }

    public void setRightTextColor(int color) {
        tvRight.setTextColor(color);
    }

    public void setLeftTitle(String title) {
        tvLeft.setText(title);
    }

    public void setRightTitle(String title) {
        tvRight.setText(title);
    }

    public void setRightTitle(int title) {
        tvRight.setText(title);
    }

    public void setMiddleTitle(int titleId) {
        tvMiddle.setText(titleId);
    }

    public void setLeftAlpha(float alpha) {
        tvLeft.setAlpha(alpha);
    }

    public void setRightAlpha(float alpha) {
        tvRight.setAlpha(alpha);
    }

    public void setMiddleAlpha(float alpha) {
        tvMiddle.setAlpha(alpha);
    }

    public void setMiddleTitle(String title) {
        tvMiddle.setText(title);
    }

    public void setTitleBackground(@ColorInt int color) {
        rootView.setBackgroundColor(color);
    }

    public void setTitleAlpha(float alpha) {
        rootView.setAlpha(alpha);
    }

    public void setRightEnabled(boolean isEnable) {
        tvRight.setEnabledClick(isEnable);
    }

    public void setMiddleTitle(String title, boolean haveLeft) {
        if (haveLeft) {
            this.setLeftImage(R.drawable.title_left_back_arrow);
        }
        tvMiddle.setText(title);
    }

    public void setMiddleTitleImage(@StringRes int titleId, @DrawableRes int resId) {
        setMiddleTitle(titleId);
        if (-1 != resId) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvMiddle.setCompoundDrawablePadding(SizeUtils.dp2px(getContext(),8));
            tvMiddle.setCompoundDrawables(drawable, null, null, null);
        }
    }

  public void setLeftImage(@DrawableRes int leftImage) {
    setLeftTitle(leftTitle);
    if (-1 != leftImage) {
      Drawable drawable = ContextCompat.getDrawable(getContext(), leftImage);
      drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
      tvLeft.setCompoundDrawablePadding(SizeUtils.dp2px(getContext(),8));
      tvLeft.setCompoundDrawables(drawable, null, null, null);
    }
  }

  public void setLeftImage(@DrawableRes int leftImage, int  maginLeft) {
    setLeftTitle(leftTitle);
    if (-1 != leftImage) {
      Drawable drawable = ContextCompat.getDrawable(getContext(), leftImage);
      drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
      tvLeft.setCompoundDrawablePadding(SizeUtils.dp2px(getContext(),8));
      tvLeft.setCompoundDrawables(drawable, null, null, null);
      LayoutParams layoutParams = (LayoutParams) tvLeft.getLayoutParams();
      layoutParams.leftMargin = SizeUtils.dp2px(getContext(),maginLeft);
    }
  }

    public void setRightImage(int rightImage) {
        setRightTitle(rightTitle);
        Drawable drawable = getResources().getDrawable(rightImage);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvRight.setCompoundDrawablePadding(SizeUtils.dp2px(getContext(),8));
        tvRight.setCompoundDrawables(null, null, drawable, null);
    }

    public CustomTextView getTvRight() {
        return tvRight;
    }

    public void setRightImage(int rightImage1, int rightImage2) {
        setRightTitle(rightTitle);
        Drawable drawable1 = getResources().getDrawable(rightImage1);
        Drawable drawable2 = getResources().getDrawable(rightImage2);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        tvRight.setCompoundDrawablePadding(SizeUtils.dp2px(getContext(),8));
        tvRight.setCompoundDrawables(drawable1, null, drawable2, null);
    }

    public void setLineVisibility(int visibility) {
        title_line.setVisibility(visibility);
    }

    public TextView getMiddleView() {
        return tvMiddle;
    }

    public View getLineView() {
        return title_line;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tvLeftTitle) {
            if (listener != null) {
                listener.onLeftClick();
            }
        }
    }

    public void setTitleClickListener(TitleUpdateListener titleUpdateListener) {
        this.listener = titleUpdateListener;
    }

    public interface TitleClickListener {

        void onLeftClick();

        void onRightClick();

        void onRightButtonLeftClick();

        void onRightButtonRightClick();
    }

    public abstract static class TitleUpdateListener implements TitleClickListener {
        @Override
        public void onLeftClick() {

        }

        @Override
        public void onRightClick() {

        }

        @Override
        public void onRightButtonLeftClick() {

        }

        @Override
        public void onRightButtonRightClick() {

        }
    }
}
