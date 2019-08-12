package com.soyoung.component_base.widget.tablayout;

import android.support.annotation.DrawableRes;

public interface CustomTabEntity {
    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();

    void setSelectedIcon(int selectedIcon);


   void setUnSelectedIcon(int unSelectedIcon);


}