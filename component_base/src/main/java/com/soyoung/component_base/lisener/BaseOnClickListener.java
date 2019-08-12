package com.soyoung.component_base.lisener;

import android.view.View;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/7/21.
 */
public abstract class BaseOnClickListener implements View.OnClickListener {

  //点击的间隔
  public static final int MIN_CLICK_DELAY_TIME = 800;
  //上一次的点击时间
  private long lastClickTime = 0;

  //6.3.7版本的
  public void onClick(View v) {
    long currentTime = Calendar.getInstance().getTimeInMillis();
    if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
      lastClickTime = currentTime;
      try {
        onViewClick(v);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public abstract void onViewClick(View v);
}
