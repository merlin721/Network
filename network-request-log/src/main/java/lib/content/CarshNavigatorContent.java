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
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import httploglib.lib.R;
import io.mattcarroll.hover.Content;
import lib.NetworkRequestUtil;
import lib.adapter.MBaseAdapter;
import lib.util.FileUtils;

/**
 * 错误carsh导航
 */
public class CarshNavigatorContent extends FrameLayout implements Content {
    private static final String TAG = "CarshNavigatorContent";
    public List<String> lists;
    private LinearLayout carshResult;
    private LinearLayout carshResultList;
    private TextView tvCarsh;
    Context context;
    private CarshAdapter carshAdapter;
    private String crashFilePath;
    private TextView device_info;


    public CarshNavigatorContent(@NonNull Context context) {
        super(context);
        this.context = context;
        lists = new ArrayList<>();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.carsh_result_list, this, true);
        ListView listview = (ListView) findViewById(R.id.listview);
        carshResult = (LinearLayout) findViewById(R.id.carsh_result);
        carshResultList = (LinearLayout) findViewById(R.id.carsh_result_list);
        tvCarsh = (TextView) findViewById(R.id.tv_carsh);
        device_info = (TextView) findViewById(R.id.device_info);
        Button bt_copy = (Button) findViewById(R.id.bt_copy);


        carshAdapter = new CarshAdapter(context, lists);
        listview.setAdapter(carshAdapter);


        Button bt_clear = (Button) findViewById(R.id.bt_clear);
        Button bt_close = (Button) findViewById(R.id.bt_close);
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lists.clear();
                carshAdapter.notifyDataSetChanged();
                try {
                    FileUtils.deleteFolderFile(crashFilePath, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showResult();
                String read = readFileFromSDCard(crashFilePath + File.separatorChar + lists.get(position));
                if (!TextUtils.isEmpty(read)) {
                    String deviceInfo = NetworkRequestUtil.getInstance().getDeviceInfo();
                    device_info.setText(deviceInfo);
                    tvCarsh.setText(read);
                }
            }
        });
        bt_copy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = tvCarsh.getText();
                NetworkRequestUtil.copy(context, text.toString());
            }
        });

        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResultList();
            }
        });
    }

    public void selectCarsh(String filePath) {
        File dir = new File(filePath);
        File[] files = dir.listFiles();
        lists.clear();
        if (null != files && files.length > 0) {
            int length = files.length - 1;
            for (int i = length; i > 0; i--) {
                String name = files[i].getName();
                lists.add(name);
            }
        }
    }

    private void showResult() {
        carshResult.setVisibility(View.VISIBLE);
        carshResultList.setVisibility(View.GONE);
    }

    private void showResultList() {
        carshResultList.setVisibility(View.VISIBLE);
        carshResult.setVisibility(View.GONE);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public boolean isFullscreen() {
        return false;
    }

    @Override
    public void onShown() {
        crashFilePath = NetworkRequestUtil.getInstance().getCrashFilePath();
        selectCarsh(crashFilePath);//获取carsh列表
        carshAdapter.setList(lists);
    }

    @Override
    public void onHidden() {
        showResultList();
    }


    /**
     * @param fileName
     * @return
     * @desc 读取文件内容
     */
    public String readFileFromSDCard(String fileName) {
        try {
            FileInputStream fin = new FileInputStream(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            fin.close();
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    class CarshAdapter extends MBaseAdapter<String> {

        public CarshAdapter(Context context, List<String> list) {
            super(context, list);
        }

        @Override
        public int getItemResource() {
            return R.layout.carsh_result_item;
        }

        @Override
        public void getItemView(int position, View convertView, ViewHolder holder) {
            String item = getItem(position);
            TextView tv = (TextView) holder.getView(R.id.tv_carsh_item);
            tv.setText(item);
        }
    }
}
