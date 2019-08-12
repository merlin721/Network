package com.merlin.time.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.login.presenter.LoginPresenter;
import com.soyoung.component_base.mvp.factory.CreatePresenter;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.ToastUtils;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/**
 * @author merlin
 * @date desc 登录界面
 */
@CreatePresenter(LoginPresenter.class)
public class LoginActivity extends BaseActivity implements LoginView{

    private EditText inputEd;
    private TextView enterTv;
    private ImageView cancelImg;
    private LoginPresenter loginPresenter;

    @Override protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override protected void initView() {
        inputEd = findViewById(R.id.login_ed);
        enterTv = findViewById(R.id.login_enter_tv);
        cancelImg = findViewById(R.id.login_cancel);
        //inputEd.setText("15201253221");
    }

    @Override protected void initData(@Nullable Bundle savedInstanceState) {
        loginPresenter = (LoginPresenter) getMvpPresenter();
    }
    /**
     * * 初始化沉浸式
     */
    @Override
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
            .statusBarDarkFont(true, 0.2f)
            .navigationBarWithKitkatEnable(false)
            .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
            .init();
    }
    @Override protected void setListener() {

        RxView.clicks(cancelImg)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    inputEd.getText().clear();
                }
            });
        RxView.clicks(enterTv)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    String phoneNum = inputEd.getText().toString();
                    loginPresenter.login(phoneNum);
                    ToastUtils.showIconToast(LoginActivity.this,"登录成功");
                }
            });

        inputEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    cancelImg.setVisibility(View.GONE);
                } else {
                    cancelImg.setVisibility(View.VISIBLE);
                }
                if (s.length() == 11) {
                    enterTv.setClickable(true);
                    enterTv.setBackgroundResource(R.drawable.login_ic_go_press);
                } else {
                    enterTv.setClickable(false);
                    enterTv.setBackgroundResource(R.drawable.login_ic_go_normal);
                }
            }
        });
    }


    @Override public void goCode(String phone) {
        Intent intent = new Intent(this,LoginCodeActivity.class);
        intent.putExtra("phone",phone);
        startActivity(intent);
        finish();
    }
}
