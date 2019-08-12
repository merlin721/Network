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
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import httploglib.lib.R;
import io.mattcarroll.hover.Content;
import lib.NetworkRequestUtil;
import lib.data.HttpTransaction;

/**
 * {@link NavigatorContent} that displays an introduction to Hover.
 */

/**
 * @author liuml.
 * @explain http 界面
 * @time 2017/12/22 20:37
 */
public class HttpNavigatorContent extends FrameLayout implements Content{
    private static final String TAG=HttpNavigatorContent.class.getSimpleName();
    Context context;
    private LinearLayout httpResult;
    private LinearLayout httpResultList;
    private int clickPosition = 0;
    ListHttpAdapter listHttpAdapter;
    TextView url;
    TextView method;
    TextView protocol;
    TextView status;
    TextView response;
    TextView ssl;
    TextView requestTime;
    TextView responseTime;
    TextView duration;
    TextView requestSize;
    TextView responseSize;
    TextView totalSize;
    private TextView requestHeaders;
    private TextView requestBody;
    private TextView responseHeaders;
    private TextView reponseBody;
    private Button btCopy;
    private Button btClose;

    public HttpNavigatorContent(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.result_list, this, true);
        httpResult = (LinearLayout) findViewById(R.id.http_result);
        httpResultList = (LinearLayout) findViewById(R.id.http_result_list);
        btCopy = (Button) findViewById(R.id.bt_copy);
        Button btClear = (Button) findViewById(R.id.bt_clear);
        ListView listview = (ListView) findViewById(R.id.listview);
        btClose = (Button) findViewById(R.id.bt_close);


        listHttpAdapter = new ListHttpAdapter(context);
        listview.setAdapter(listHttpAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showResult();
                clickPosition = position;
                ListHttpAdapter adapter = (ListHttpAdapter) parent.getAdapter();
                HttpTransaction item = adapter.getItem(position);
                setHttpInfoData(item);
            }
        });

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkRequestUtil.getInstance().clearNetworkRequest();
                listHttpAdapter.removeAll();
            }
        });

        //拷贝返回的数据
        btCopy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpTransaction item = listHttpAdapter.getItem(clickPosition);
                NetworkRequestUtil.copy(context, "返回数据: " + item.getFormattedResponseBody());
            }
        });
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResultList();
            }
        });

        //数据详情的控件
        url = (TextView) findViewById(R.id.url);
        method = (TextView) findViewById(R.id.method);
        protocol = (TextView) findViewById(R.id.protocol);
        status = (TextView) findViewById(R.id.status);
        response = (TextView) findViewById(R.id.response);
        ssl = (TextView) findViewById(R.id.ssl);
        requestTime = (TextView) findViewById(R.id.request_time);
        responseTime = (TextView) findViewById(R.id.response_time);
        duration = (TextView) findViewById(R.id.duration);
        requestSize = (TextView) findViewById(R.id.request_size);
        responseSize = (TextView) findViewById(R.id.response_size);
        totalSize = (TextView) findViewById(R.id.total_size);

        requestHeaders = (TextView) findViewById(R.id.request_headers);
        requestBody = (TextView) findViewById(R.id.request_body);
        responseHeaders = (TextView) findViewById(R.id.response_headers);
        reponseBody = (TextView) findViewById(R.id.reponse_body);
    }

    private void showResult() {
        httpResult.setVisibility(View.VISIBLE);
        httpResultList.setVisibility(View.GONE);
    }

    private void showResultList() {
        httpResultList.setVisibility(View.VISIBLE);
        httpResult.setVisibility(View.GONE);

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
        ArrayList<HttpTransaction> requestData = NetworkRequestUtil.getInstance().getRequestData();
        listHttpAdapter.setList(requestData);
    }
    @Override
    public void onHidden() {
        ArrayList<HttpTransaction> requestData = NetworkRequestUtil.getInstance().getRequestData();
        listHttpAdapter.setList(requestData);
    }

    private void setHttpInfoData(HttpTransaction transaction) {
        url.setText(transaction.getUrl());
        method.setText(transaction.getMethod());
        protocol.setText(transaction.getProtocol());
        status.setText(transaction.getStatus().toString());
        response.setText(transaction.getResponseSummaryText());
        ssl.setText((transaction.isSsl() ? R.string.chuck_yes : R.string.chuck_no));
        requestTime.setText(transaction.getRequestDateString());
        responseTime.setText(transaction.getResponseDateString());
        duration.setText(transaction.getDurationString());
        requestSize.setText(transaction.getRequestSizeString());
        responseSize.setText(transaction.getResponseSizeString());
        totalSize.setText(transaction.getTotalSizeString());

        requestHeaders.setVisibility((TextUtils.isEmpty(transaction.getRequestHeadersString(true)) ? View.GONE : View.VISIBLE));
        responseHeaders.setVisibility((TextUtils.isEmpty(transaction.getResponseHeadersString(true)) ? View.GONE : View.VISIBLE));

        requestHeaders.setText("requestHeaders  " + Html.fromHtml(transaction.getRequestHeadersString(true)));
        responseHeaders.setText("responseHeaders  " + Html.fromHtml(transaction.getResponseHeadersString(true)));

        setText(1, requestBody, transaction.getFormattedRequestBody(), transaction.requestBodyIsPlainText());
        setText(2, reponseBody, transaction.getFormattedResponseBody(), transaction.responseBodyIsPlainText());
    }

    private void setText(int type, TextView textView, String bodyString, boolean isPlainText) {
        if (!isPlainText) {
            textView.setText(context.getString(R.string.chuck_body_omitted));
        } else {
            if (type == 1) {
                textView.setText("requestBody " + bodyString);
            } else {
                textView.setText("reponseBody " + bodyString);
            }
        }
    }
}
