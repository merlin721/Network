package com.merlin.time.my.feedback;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.my.feedback.adapter.FeedbackAdapter;
import com.merlin.time.my.feedback.model.FeedbackBaseModel;
import com.merlin.time.my.feedback.model.FeedbackListBaseBean;
import com.merlin.time.my.feedback.model.FeedbackListBean;
import com.merlin.time.network.AppNetWorkHelper;
import com.merlin.time.utils.RecyclerViewItemDec;
import com.merlin.time.view.TimeTextView;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.SizeUtils;
import com.soyoung.component_base.util.ToastUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

/**
 * @author merlin
 * @date 2018年11月11日
 * desc 关于
 */
public class FeedbackActivity extends BaseActivity {
    private ImageView back;
    private TimeTextView sendTv;
    private EditText inputEt;

    private RecyclerView recyclerView;

    private FeedbackAdapter adapter;

    List<FeedbackListBean> mContentData;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.feedback_back_img);
        sendTv = findViewById(R.id.feedback_send_tv);
        inputEt = findViewById(R.id.feedback_input);
        recyclerView = findViewById(R.id.feedback_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FeedbackAdapter(this);
        recyclerView.setAdapter(adapter);

        RecyclerViewItemDec itemDecoration = new RecyclerViewItemDec(SizeUtils.dp2px(context,10));
        recyclerView.addItemDecoration(itemDecoration);

    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarDarkFont(true, 0.2f)
                .fitsSystemWindows(true)
                .statusBarColor(com.soyoung.component_base.R.color.ffffff)
                .navigationBarWithKitkatEnable(false)
                .keyboardEnable(true)
                .setOnKeyboardListener(new OnKeyboardListener() {
                    @Override
                    public void onKeyboardChange(boolean b, int i) {
                        if (b){
                            recyclerView.smoothScrollToPosition(adapter.getItemCount());
                        }
                    }
                })
                .init();
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mContentData = new ArrayList<>();
        Disposable disposable = AppNetWorkHelper.getInstance().feedbackList("2")
                .compose(toMain())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        FeedbackListBaseBean model = new Gson().fromJson(jsonObject.toString(), FeedbackListBaseBean.class);
                        if (model != null && "1".equals(model.ret)) {
                            mContentData.clear();
                            mContentData.addAll(model.getData());
                            adapter.setmContentData(mContentData);
                            adapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(adapter.getItemCount());
                        } else {
                            ToastUtils.showToast(context, "接口请求失败");
                        }
                    }
                });
        getCompositeDisposable().add(disposable);
    }

    @Override
    protected void setListener() {
        Disposable disposable = RxView.clicks(back)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
                    }
                });

        Disposable disposable1 = RxView.clicks(sendTv)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        send();
                    }
                });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftInput();
                return false;
            }
        });

    }


    private void send() {
        String content = inputEt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast(FeedbackActivity.this, "请输入内容");
            return;
        }
        Disposable disposable = AppNetWorkHelper.getInstance().sendFeedback(content)
                .compose(toMain())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        Gson gson = new Gson();
                        FeedbackBaseModel model = gson.fromJson(jsonObject.toString(), FeedbackBaseModel.class);
                        if (!TextUtils.isEmpty(model.ret) && "1".equals(model.ret)) {
//                            ToastUtils.showToast(FeedbackActivity.this, "反馈成功");
//                            finish();
                            FeedbackListBean data = new FeedbackListBean();
                            data.setType("1");
                            data.setContent(content);
                            inputEt.setText("");
                            mContentData.add(data);
                            adapter.setmContentData(mContentData);
                            adapter.notifyItemInserted(mContentData.size());
                            recyclerView.smoothScrollToPosition(adapter.getItemCount());

                        } else {
                            ToastUtils.showToast(FeedbackActivity.this, model.error.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        getCompositeDisposable().add(disposable);
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput() {
        if (getWindow().getAttributes().softInputMode
                != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);;
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
