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
import android.widget.TextView;
import com.soyoung.component_base.R;
import com.soyoung.component_base.lisener.BaseOnClickListener;
import com.soyoung.component_base.lisener.IPostDelete;
import com.soyoung.component_base.lisener.IPostFocus;
import com.soyoung.component_base.lisener.IPostImgClick;
import com.soyoung.component_base.lisener.IPostTextChangerLisener;
import com.soyoung.component_base.lisener.ISettingCover;
import com.soyoung.component_base.util.ImageUtils;
import com.soyoung.component_base.util.SizeUtils;
import com.soyoung.component_base.util.ToastUtils;

/**
 * Created by huitailang on 16/8/23.
 * 700添加设置封面
 */
public class CustomPostImg extends APostParent implements View.OnClickListener {

  public EditText describe_next_edit;//description layout
  public boolean isCover = false;
  public boolean isShowSettingCover = false;
  View popView;
  private ImageView img;
  private Button img_del;
  private EditText describe_edit;//description layout
  private IPostDelete deleInter;//删除
  private IPostImgClick clickInter;
  private IPostFocus postFocus;
  private IPostTextChangerLisener postTextChangerLisener;
  private ISettingCover settingCover;
  private String mImgPath = "";
  private String mImgUploadPath = "";
  private ImageView setting_cover;

  public CustomPostImg(Context context, boolean isDiary) {
    super(context);
    this.isShowSettingCover = isDiary;
  }

  public CustomPostImg(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomPostImg(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  void initView() {
    View.inflate(context, R.layout.layout_custom_post_img, this);
    setTag(System.currentTimeMillis());
    img = findViewById(R.id.post_img);
    img_del = findViewById(R.id.img_del);
    setting_cover = findViewById(R.id.setting_cover);
    describe_edit = findViewById(R.id.describe_edit);//mark edit
    describe_next_edit = findViewById(R.id.describe_next_edit);//description next edit

    img_del.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (deleInter != null) {
          deleInter.delete(Long.parseLong(getTag().toString()), POST_TYPE_IMG);
        }
      }
    });
    img.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
      }
    });
    describe_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE || (
            event != null
                && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                && KeyEvent.ACTION_DOWN == event.getAction())) {
          describe_next_edit.requestFocus();
        }
        return true;
      }
    });

    describe_edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        postFocus.focus(v.hasFocus(), v);
      }
    });

    describe_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (postFocus != null) {
          postFocus.focus(hasFocus, v);
        }
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
        if (!TextUtils.isEmpty(s)) {
          int sLength = s.length();
          if (sLength + after >= 40 && after != 0) {
            ToastUtils.cancleToast();
            ToastUtils.showToast(context, "文案太长，精简一下呦");
          }
        }
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });
    describe_next_edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (postFocus != null) {
          postFocus.focus(v.hasFocus(), v);
        }
      }
    });
    describe_next_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (postFocus != null) {
          postFocus.focus(hasFocus, v);
        }
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
    initPopuWindow();
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
    double aspectRatio = ImageUtils.getBitmapWtoHResize(imgPath);
    double width = SizeUtils.getDisplayWidth(context) - SizeUtils.dpToPx(context, 30);
    img.getLayoutParams().height = (int) (width / aspectRatio);
    displayImage(imgPath, img, R.drawable.load_main_background, (int) width,
        img.getLayoutParams().height);
  }

  /**
   * 获取文本信息
   */
  public String getText() {
    return TextUtils.isEmpty(describe_next_edit.getText()) ? ""
        : describe_next_edit.getText().toString();
  }

  /**
   * set文本信息
   */
  public void setText(String text) {
    describe_next_edit.setText(text);
  }

  @Override
  public void setText(SpannableString text) {
    describe_next_edit.setText(text);
  }

  /**
   * 获取备注信息
   */
  public String getMarkInfo() {
    return TextUtils.isEmpty(describe_edit.getText()) ? "" : describe_edit.getText().toString();
  }

  public void setMarkInfo(SpannableString markInfo) {
    if (!TextUtils.isEmpty(markInfo)) {
      describe_edit.setVisibility(View.VISIBLE);
      describe_edit.setText(markInfo);
    }
  }

  /**
   * set备注信息
   */
  public void setMarkInfo(String markInfo) {
    if (!TextUtils.isEmpty(markInfo)) {
      describe_edit.setVisibility(View.VISIBLE);
      describe_edit.setText(markInfo);
    }
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
    return POST_TYPE_IMG;
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

  public void setPopView(View popView) {
    this.popView = popView;
  }

  @Override
  public void onClick(View v) {
  }

  public void setCover() {
    isCover = true;
    setting_cover.setVisibility(View.VISIBLE);
  }

  public void cancleCover() {
    isCover = false;
    setting_cover.setVisibility(View.GONE);
  }

  public void initPopuWindow() {
    if (isCover || !isShowSettingCover) {
      setting_cover.setVisibility(View.GONE);
    } else {
      setting_cover.setVisibility(View.VISIBLE);
    }
    setting_cover.setOnClickListener(new BaseOnClickListener() {
      @Override
      public void onViewClick(View v) {
        if (isCover) {
          cancleCover();
          if (settingCover != null) {
            settingCover.cancleCover();
          }
        } else {
          if (settingCover != null) {
            settingCover.settingCover();
            isCover = true;
          }
          setCover();
        }
      }
    });
  }

  public void setISettingCover(ISettingCover settingCover) {
    this.settingCover = settingCover;
  }

}
