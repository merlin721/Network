package com.soyoung.component_base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by sentry on 2015/9/22.
 */
public class CustomHScrollView extends HorizontalScrollView {
  private static final String TAG = "CustomHScrollView";
  private GestureDetector mGestureDetector;
  private OnTouchListener mGestureListener;

  /**
   * @param context Interface to global information about an application environment.
   * @function CustomHScrollView constructor
   */
  public CustomHScrollView(Context context) {
    super(context);
    mGestureDetector = new GestureDetector(context, new HScrollDetector());
    setFadingEdgeLength(0);
  }

  /**
   * @param context Interface to global information about an application environment.
   * @param attrs A collection of attributes, as found associated with a tag in an XML document.
   * @function CustomHScrollView constructor
   */
  public CustomHScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mGestureDetector = new GestureDetector(context, new HScrollDetector());
    setFadingEdgeLength(0);
  }

  /**
   * @param context Interface to global information about an application environment.
   * @param attrs A collection of attributes, as found associated with a tag in an XML document.
   * @param defStyle style of view
   * @function CustomHScrollView constructor
   */
  public CustomHScrollView(Context context, AttributeSet attrs,
      int defStyle) {
    super(context, attrs, defStyle);
    mGestureDetector = new GestureDetector(context, new HScrollDetector());
    setFadingEdgeLength(0);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
  }

  // Return false if we're scrolling in the y direction
  class HScrollDetector extends SimpleOnGestureListener {
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      if (Math.abs(distanceX) > Math.abs(distanceY)) {
        return true;
      }

      return false;
    }
  }
}
