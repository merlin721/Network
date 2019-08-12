/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lib;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import io.mattcarroll.hover.Content;
import io.mattcarroll.hover.HoverMenu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lib.theming.HoverTheme;
import lib.ui.TabView;

/**
  *DemoHoverMenuAdapter
  *@author ：daiwenbo
  *@Time   ：2018/6/13 下午2:23
  *@e-mail ：daiwwenb@163.com
  *@description ：MenuAdapter
  *
  */
public class DemoHoverMenuAdapter extends HoverMenu {
    public static final String NET = "net";//net 网络请求
    public static final String CARSH_ID = "carsh";//carsh 列表
    public static final String IP_SWITCH = "ip_switch";//IP地址切换 服务器切换地址
    public static final String AROUTER = "arouter";//路由

    private final Context mContext;
    private final String mMenuId;
    private final List<Section> mSections = new ArrayList<>();

    public DemoHoverMenuAdapter(@NonNull Context context, @NonNull String menuId,
        @NonNull Map<String, Content> data) throws IOException {
        mContext = context;
        mMenuId = menuId;
        for (String tabId : data.keySet()) {
            mSections.add(new Section(new SectionId(tabId), createTabView(tabId), data.get(tabId)));
        }
    }
    public void setTheme(@NonNull HoverTheme theme) {
        notifyMenuChanged();
    }

    public View createTabView(String menuItemId) {
        if (NET.equals(menuItemId)) {
            int color = Color.parseColor("#6a5acd");
            return createTabView(color, Color.WHITE, "网络");
        } else if (CARSH_ID.equals(menuItemId)) {
            int color = Color.parseColor("#dc143c");
            return createTabView(color, Color.WHITE, "Crash");
        } else if (IP_SWITCH.equals(menuItemId)) {
            int color = Color.parseColor("#228b22");
            return createTabView(color, Color.WHITE, "服务");
        } else if (AROUTER.equals(menuItemId)) {
            int color = Color.parseColor("#ff31CAC4");
            return createTabView(color, Color.WHITE, "路由");
        } else {
            throw new RuntimeException("Unknown tab selected: " + menuItemId);
        }
    }

    private View createTabView(int backColor, @ColorInt int textColor, String tabName) {
        Resources resources = mContext.getResources();
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
            resources.getDisplayMetrics());
        View view = new TabView(mContext, backColor, textColor, tabName);
        view.setPadding(padding, padding, padding, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(padding);
        }
        return view;
    }
    @Override
    public String getId() {
        return mMenuId;
    }

    @Override
    public Section getSection(int index) {
        return mSections.get(index);
    }

    @Nullable
    @Override
    public Section getSection(@NonNull SectionId sectionId) {
        for (Section section : mSections) {
            if (section.getId().equals(sectionId)) {
                return section;
            }
        }
        return null;
    }
    @Override
    public int getSectionCount() {
        return  mSections.size();
    }
    @NonNull
    @Override
    public List<Section> getSections() {
        return new ArrayList<>(mSections);
    }
}
