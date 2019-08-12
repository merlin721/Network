package com.soyoung.component_base.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * author : wangwenwang
 * e-mail : wangwenwang@soyoung.com
 * date   : 2018/8/30
 * description   : 图文混排TextView(解决：超过最大行数末尾显示"..."，后面跟显图片)
 */
public class EllipsizedTextView extends AppCompatTextView {

  private CharSequence mText;
  private float mLineSpacingMultiplier;

  public EllipsizedTextView(Context context) {
    super(context);
    init(context, null, 0);
  }

  public EllipsizedTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0);
  }

  public EllipsizedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    //        final TypedArray a = context.obtainStyledAttributes(attrs
    //                , new int[]{android.R.attr.lineSpacingMultiplier}
    //                , defStyleAttr
    //                , 0);
    //
    //        mLineSpacingMultiplier = a.getFloat(0, 0);
    //        a.recycle();
  }

  @Override
  public void setText(CharSequence text, BufferType type) {
    mText = text;
    int maxLines = getMaxLines();

    if (!TextUtils.isEmpty(text)
        && (maxLines != Integer.MAX_VALUE && maxLines > 1)
        && getWidth() != 0) {
      StaticLayout layout =
          new StaticLayout(text, getPaint(), getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f,
              false);
      // 需要显示的文字加上"..."的总宽度
      float textAndEllipsizeWidth = 0;
      int lines = Math.min(maxLines, layout.getLineCount());
      for (int i = 0; i < lines; i++) {
        // 此处用getWidth()计算的话会有误差，所以用getLineWidth()
        textAndEllipsizeWidth += layout.getLineWidth(i);
      }
      text = TextUtils.ellipsize(text, getPaint(), textAndEllipsizeWidth, TextUtils.TruncateAt.END);
    }
    super.setText(text, type);
  }

  @Override
  protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    super.onSizeChanged(width, height, oldWidth, oldHeight);
    if (width > 0 && oldWidth != width) {
      setText(mText);
    }
  }

  @Override
  public void setMaxLines(int maxLines) {
    super.setMaxLines(maxLines);
    setText(mText);
  }
}
