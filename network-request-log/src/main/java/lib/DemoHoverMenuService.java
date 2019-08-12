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
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.ContextThemeWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import httploglib.lib.R;
import io.mattcarroll.hover.HoverView;
import io.mattcarroll.hover.window.HoverMenuService;
import lib.data.CloseMenu;
/**
  *DemoHoverMenuService
  *@author ：daiwenbo
  *@Time   ：2018/6/13 下午2:24
  *@e-mail ：daiwwenb@163.com
  *@description ：DemoHoverMenuService
  *
  */
public class DemoHoverMenuService extends HoverMenuService {
    private static final String TAG = DemoHoverMenuService.class.getSimpleName();

    public static void showFloatingMenu(Context context) {
        boolean serviceWork = NetworkRequestUtil.getInstance().isServiceWork(context, TAG);
        if (serviceWork) {
            hideFloatingMenu(context);
        }
        context.startService(new Intent(context, DemoHoverMenuService.class));
    }

    public static void hideFloatingMenu(Context context) {
        context.stopService(new Intent(context, DemoHoverMenuService.class));
    }

    private DemoHoverMenuAdapter mDemoHoverMenuAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected Context getContextForHoverMenu() {
        return new ContextThemeWrapper(this, R.style.AppTheme);
    }

    @Override
    protected void onHoverMenuLaunched(@NonNull Intent intent, @NonNull HoverView hoverView) {
        hoverView.setMenu(createHoverMenu());
        hoverView.collapse();
    }

    protected DemoHoverMenuAdapter createHoverMenu() {
        try {
            mDemoHoverMenuAdapter = new DemoHoverMenuFactory().createDemoMenuFromCode(getContextForHoverMenu());
            return mDemoHoverMenuAdapter;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(@NonNull CloseMenu closeMenu) {
        if (closeMenu.type == 0) {
            getHoverView().collapse();
        } else {
            getHoverView().close();
        }
    }

}
