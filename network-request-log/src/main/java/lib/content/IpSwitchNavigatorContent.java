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
package lib.content;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import httploglib.lib.R;
import io.mattcarroll.hover.Content;
import lib.NetworkRequestUtil;
import lib.adapter.MBaseAdapter;
import lib.data.IpConfigBeen;

/**
 * ip 选择 导航
 */
public class IpSwitchNavigatorContent extends FrameLayout implements Content {
    private static final String TAG = IpSwitchNavigatorContent.class.getSimpleName();
    private IpSwitchAdapter ipSwitchAdapter;
    private LinearLayout content;
    private FrameLayout empty_text;

    public IpSwitchNavigatorContent(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.result_ip_list, this, true);
        ListView listview = (ListView) findViewById(R.id.listview_ip);
        content = (LinearLayout) findViewById(R.id.content);
        empty_text = (FrameLayout) findViewById(R.id.empty_text);

        ipSwitchAdapter = new IpSwitchAdapter(context);
        listview.setAdapter(ipSwitchAdapter);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NetworkRequestUtil.getInstance().setSwitchIp(position);
                return true;
            }
        });
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public boolean isFullscreen() {
        return true;
    }


    @Override
    public void onShown() {
        ArrayList<IpConfigBeen> ipList = NetworkRequestUtil.getInstance().getIpList();
        if (ipList.size() > 0) {
            empty_text.setVisibility(GONE);
            content.setVisibility(VISIBLE);
            ipSwitchAdapter.setList(ipList);
        } else {
            content.setVisibility(GONE);
            empty_text.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onHidden() {

    }

    class IpSwitchAdapter extends MBaseAdapter<IpConfigBeen> {
        String[] colors = { "#FECD52", "#12A058", "#2781BB", "#00A058" };
        int switchs;

        public IpSwitchAdapter(Context context) {
            super(context);
            switchs = NetworkRequestUtil.getInstance().getSwitchIndex();
        }

        @Override
        public int getItemResource() {
            return R.layout.result_ip_list_item;
        }

        @Override
        public void getItemView(int position, View convertView, ViewHolder holder) {
            IpConfigBeen ipConfigBeen = getItem(position);
            LinearLayout list_url = holder.getView(R.id.list_url);
            TextView environmentName = holder.getView(R.id.environmentName);

            HashMap<String, String> urlMap = ipConfigBeen.getUrlMap();
            Set<Map.Entry<String, String>> entries = urlMap.entrySet();
            if (list_url.getChildCount()==0) {
                int i=0;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL);
                for (Map.Entry<String, String> entry : entries) {
                    View urlView = LayoutInflater.from(context).inflate(R.layout.item_url_layout, null);
                    TextView urlTextView = urlView.findViewById(R.id.url);
                    TextView urlNameTextView = urlView.findViewById(R.id.url_name);
                    urlTextView.setText(entry.getValue());
                    urlNameTextView.setText(entry.getKey());
                    urlView.setId(i);
                    list_url.addView(urlView, params);
                    i++;
                }
            }else {
                int i=0;
                for (Map.Entry<String, String> entry : entries) {
                    View urlView=holder.getView(i);
                    if (null!=urlView) {
                        TextView urlTextView = urlView.findViewById(R.id.url);
                        TextView urlNameTextView = urlView.findViewById(R.id.url_name);
                        urlTextView.setText(entry.getValue());
                        urlNameTextView.setText(entry.getKey());
                    }
                    i++;
                }
            }
            list_url.setBackgroundColor(Color.parseColor(colors[position]));
            environmentName.setText(ipConfigBeen.getEnvironmentName());
            if (switchs == position) {
                Drawable drawable = ContextCompat.getDrawable(context,  R.drawable.check_ip);
                environmentName.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
            } else {
                environmentName.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }
        }
    }
}
