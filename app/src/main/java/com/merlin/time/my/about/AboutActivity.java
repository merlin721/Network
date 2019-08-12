package com.merlin.time.my.about;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.entity.BaseMode;
import com.merlin.time.login.LoginActivity;
import com.merlin.time.login.model.LoginCodeModel;
import com.merlin.time.main.MainActivity;
import com.merlin.time.network.AppNetWorkHelper;
import com.merlin.time.view.TimeTextView;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.ToastUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/**
 * @author merlin
 * @date 2018年11月11日
 * desc 关于
 */
public class AboutActivity extends BaseActivity {
    private ImageView back;

    @Override protected int setLayoutId() {
        return R.layout.activity_about;
    }

    @Override protected void initView() {
        back = findViewById(R.id.about_back_img);
    }

    @Override protected void initData(@Nullable Bundle savedInstanceState) {
    test();
    }

    @Override protected void setListener() {
        Disposable disposable = RxView.clicks(back)
            .throttleFirst(500,TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    finish();
                }
            });
    }

    private void test(){


    }



}
