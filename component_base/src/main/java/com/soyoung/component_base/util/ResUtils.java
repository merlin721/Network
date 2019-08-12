package com.soyoung.component_base.util;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import java.io.InputStream;

/**
 * 资源的工具类
 */

public class ResUtils {
  public static String getString(int resId) {
    return Utils.getApp().getResources().getString(resId);
  }

  public static String[] getStringArray(int resId) {
    return Utils.getApp().getResources().getStringArray(resId);
  }

  /**
   * 获取drawable下面图片，一般imageview会使用
   */
  public static Drawable getDrawable(int resId) {
    return ContextCompat.getDrawable(Utils.getApp(), resId);
  }

  public static InputStream getRaw(int resId) {
    return Utils.getApp().getResources().openRawResource(resId);
  }

  public static int getColor(int resId) {
    return ContextCompat.getColor(Utils.getApp(), resId);
  }

  public static float getDimension(int resId) {
    return Utils.getApp().getResources().getDimension(resId);
  }
}
