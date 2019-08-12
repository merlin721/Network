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
package lib.theming;

import android.support.annotation.NonNull;

/**
 * Global entry point for Hover menu theming.
 */
public class HoverThemeManager implements HoverThemer {


    private HoverThemeManager() {
    }

    private static class HoverThemeManagerLoader {
        private static final HoverThemeManager INSTANCE = new HoverThemeManager();
    }

    public static HoverThemeManager getInstance() {

        return HoverThemeManagerLoader.INSTANCE;
    }

    public void init(@NonNull HoverTheme theme) {
        setTheme(theme);
    }

    private HoverTheme mTheme;


    public HoverTheme getTheme() {
        return mTheme;
    }

    @Override
    public void setTheme(@NonNull HoverTheme theme) {
        mTheme = theme;
    }

}