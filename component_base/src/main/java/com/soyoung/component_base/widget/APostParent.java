package com.soyoung.component_base.widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.soyoung.component_base.R;
import com.soyoung.component_base.image.ImageWorker;
import com.soyoung.component_base.lisener.IPostDelete;
import com.soyoung.component_base.lisener.IPostFocus;
import com.soyoung.component_base.lisener.IPostImgClick;
import com.soyoung.component_base.lisener.IPostTextChangerLisener;
import com.soyoung.component_base.util.LogUtils;

/**
 * Created by huitailang on 2016/9/12.
 */
public abstract class APostParent extends LinearLayout {

  public static final int POST_TYPE_IMG = 0;
  public static final int POST_TYPE_VIDEO = 1;
  public static final int POST_TYPE_AUDIO = 2;
  public Context context;

  private String imgWidth = "0";
  private String imgHeight = "0";

  public APostParent(Context context) {
    super(context);
    this.context = context;
    initView();
  }

  public APostParent(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    initView();
  }

  public APostParent(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    initView();
  }

  public String getmImgHeigh() {
    return imgHeight;
  }

  public void setmImgHeigh(String imgHeight) {

    this.imgHeight = imgHeight;
  }

  public String getmImgWidth() {
    return imgWidth;
  }

  public void setmImgWidth(String imgWidth) {

    this.imgWidth = imgWidth;
  }

  abstract void initView();//init

  public abstract String getImgPath();//get img path

  public abstract void setImgPath(String imgPath);//set img pth

  public abstract String getText();

  public abstract void setText(SpannableString text);

  public abstract void setText(String text);

  public abstract void setMarkInfo(String markInfo);

  public abstract String getImgUploadPath();

  public abstract void setImgUploadPath(String mImgUploadPath);

  public abstract EditText getCanEditView();

  public abstract boolean isFocus();

  public abstract void setFocus();

  public abstract void clearFocus();

  public abstract int getImgType();

  public abstract void setOnImgDelete(IPostDelete deleInter);

  public abstract void setOnImgClick(IPostImgClick deleInter);

  public abstract void setOnPostFocus(IPostFocus postFocus);

  public abstract void setOnPostTextChange(IPostTextChangerLisener postTextChange);

  /**
   * 加载图片
   */
  protected void displayImage(String url, ImageView imageView, int resID, int width, int height) {
    if (TextUtils.isEmpty(url)) {
      return;
    }
    if (url.endsWith(".gif")) {
      ImageWorker.imageLoaderGif(context, url, imageView);
    } else {
      try {
        ImageWorker.imageLoaderRadius(context, url, imageView, R.drawable.default_image, 5);
      } catch (Exception e)//异常
      {
        LogUtils.e("APostParent displayImage is error");
        ImageWorker.imageLoader(context, imageView, R.drawable.default_image, url);
      }
    }
  }
}