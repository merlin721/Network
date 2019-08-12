package com.soyoung.component_base.widget;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.soyoung.component_base.R;
import com.soyoung.component_base.image.ImageWorker;
import com.soyoung.component_base.lisener.IPostDelete;
import com.soyoung.component_base.lisener.IPostFocus;
import com.soyoung.component_base.lisener.IPostImgClick;
import com.soyoung.component_base.lisener.IPostTextChangerLisener;
import com.soyoung.component_base.util.ImageUtils;
import com.soyoung.component_base.util.SizeUtils;

/**
 * Created by huitailang on 16/8/23.
 */
public class CustomPostVideoImg extends APostParent {
  public EditText describe_next_edit;//description layout
  private ImageView img;
  private Button img_del;
  private EditText describe_edit;//description layout
  private RelativeLayout videoLayout;

  private IPostDelete deleInter;
  private IPostFocus postFocus;
  private IPostImgClick clickInter;
  private IPostTextChangerLisener postTextChangerLisener;

  private String mImgPath = "";
  private String mImgUploadPath = "";
  private String mVideoThubmPath = "";//video 上传图片路径
  private String mVideoThubmLocalPath = "";//video 本地图片路径
  public long duration;

  public CustomPostVideoImg(Context context) {
    super(context);
  }

  public CustomPostVideoImg(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomPostVideoImg(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  void initView() {
    View.inflate(context, R.layout.layout_custom_post_video_img, this);
    setTag(System.currentTimeMillis());
    img = findViewById(R.id.post_img);
    img_del = findViewById(R.id.img_del);
    videoLayout = findViewById(R.id.post_img_layout);//video layout
    describe_edit = findViewById(R.id.describe_edit);//mark edit
    describe_next_edit = findViewById(R.id.describe_next_edit);//description next edit

    videoLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (clickInter != null) {
          clickInter.click(Long.parseLong(getTag().toString()));
        }
      }
    });
    img_del.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (deleInter != null) {
          deleInter.delete(Long.parseLong(getTag().toString()), POST_TYPE_VIDEO);
        }
      }
    });
    describe_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE || (
            event != null
                && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                && KeyEvent.ACTION_DOWN == event.getAction())) {
        }
        return false;
      }
    });

    describe_edit.setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
          if (!TextUtils.isEmpty(describe_edit.getText())) {
            describe_edit.setVisibility(View.VISIBLE);
          } else {
            describe_edit.setVisibility(View.GONE);
          }
        }
      }
    });
    describe_edit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });
    describe_next_edit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        if (null != postTextChangerLisener) {
          postTextChangerLisener.change();
        }
      }
    });
    //text
    describe_next_edit.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (postFocus != null) {
          postFocus.focus(v.hasFocus(), v);
        }
      }
    });
  }

  /**
   * 设置video本地image
   */
  public void setVideoLocalImage(String mVideoThubmLocalPath) {
    this.mVideoThubmLocalPath = mVideoThubmLocalPath;
    double aspectRatio = ImageUtils.getBitmapWtoHResize(mVideoThubmLocalPath);
    double width = SizeUtils.getDisplayWidth(context) - SizeUtils.dpToPx(context, 30);
    img.getLayoutParams().height = (int) width / 3 * 2;
    ImageWorker.imageLoaderRadius(context, "file://" + mVideoThubmLocalPath, img,
        R.drawable.load_main_background, 5);
  }

  public String getmVideoThubmLocalPath() {
    return mVideoThubmLocalPath;
  }

  @Override
  public String getImgPath() {
    return mImgPath;
  }

  /**
   * 设置img
   */
  public void setImgPath(String imgPath) {
    mImgPath = imgPath;
  }

  /**
   * 设置Video thubm路径
   */
  public void setVideoThumbPath(String mVideoThubmPath) {
    this.mVideoThubmPath = mVideoThubmPath;
  }

  public String getmVideoThubmPath() {
    return mVideoThubmPath;
  }

  /**
   * 获取文本信息
   */
  public String getText() {
    return TextUtils.isEmpty(describe_next_edit.getText()) ? ""
        : describe_next_edit.getText().toString();
  }

  @Override
  public void setText(SpannableString text) {
    describe_next_edit.setText(text);
  }

  /**
   * set文本信息
   */
  public void setText(String text) {
    describe_next_edit.setText(text);
  }

  @Override
  public void setMarkInfo(String markInfo) {
  }

  /**
   * get upload path(包含备注)
   */
  public String getImgUploadPath() {
    return mImgUploadPath;
  }

  /**
   * set upload path
   */
  public void setImgUploadPath(String mImgUploadPath) {
    this.mImgUploadPath = mImgUploadPath;
  }

  @Override public EditText getCanEditView() {
    return describe_next_edit;
  }

  public void setFocus() {
    if (describe_next_edit != null) {
      describe_next_edit.requestFocus();
    }
  }

  @Override public void clearFocus() {
    if (null != describe_next_edit) {
      describe_next_edit.clearFocus();
    }
  }

  public boolean isFocus() {
    return describe_next_edit.isFocused();
  }

  /**
   * 返回图片类型
   */
  @Override
  public int getImgType() {
    return POST_TYPE_VIDEO;
  }

  public void setOnImgDelete(IPostDelete deleInter) {
    this.deleInter = deleInter;
  }

  @Override
  public void setOnImgClick(IPostImgClick clickInter) {
    this.clickInter = clickInter;
  }

  @Override
  public void setOnPostFocus(IPostFocus postFocus) {
    this.postFocus = postFocus;
  }

  @Override public void setOnPostTextChange(IPostTextChangerLisener postTextChange) {
    this.postTextChangerLisener = postTextChange;
  }
}
