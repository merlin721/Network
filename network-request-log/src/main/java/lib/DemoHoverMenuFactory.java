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
import android.support.annotation.NonNull;
import io.mattcarroll.hover.Content;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import lib.content.ArouterNavigatorContent;
import lib.content.CarshNavigatorContent;
import lib.content.HttpNavigatorContent;
import lib.content.IpSwitchNavigatorContent;

/**
  *DemoHoverMenuFactory
  *@author ：daiwenbo
  *@Time   ：2018/6/13 下午2:24
  *@e-mail ：daiwwenb@163.com
  *@description ：HoverMenuFactory
  *
  */
public class DemoHoverMenuFactory {

    /**
     * Example of how to create a menu in code.
     *
     * @return HoverMenuAdapter
     */
    public DemoHoverMenuAdapter createDemoMenuFromCode(@NonNull Context context) throws IOException {
        Map<String, Content> demoMenu = new LinkedHashMap<>();
        demoMenu.put(DemoHoverMenuAdapter.NET, new HttpNavigatorContent(context));//网络导航
        demoMenu.put(DemoHoverMenuAdapter.CARSH_ID, new CarshNavigatorContent(context));//carsh 导航
        demoMenu.put(DemoHoverMenuAdapter.IP_SWITCH, new IpSwitchNavigatorContent(context));//ip 切换导航
        demoMenu.put(DemoHoverMenuAdapter.AROUTER, new ArouterNavigatorContent(context));//路由
        return new DemoHoverMenuAdapter(context, "homeMenu", demoMenu);
    }

}
