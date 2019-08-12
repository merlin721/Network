package com.merlin.time.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.common.CommonUtils;
import com.merlin.time.login.model.LoginCodeModel;
import com.merlin.time.main.MainActivity;
import com.merlin.time.R;
import com.merlin.time.view.TimeTextView;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.mvp.factory.CreatePresenter;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.ToastUtils;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/**
 * @author merlin
 * @date desc 登录验证码界面
 */
@CreatePresenter(LoginCodePresenter.class)
public class LoginCodeActivity extends BaseActivity implements LoginCodeView{
    /**
     * 提示
     */
    private TimeTextView tipsTv;

    private EditText codeEt;

    private TimeTextView retryTv;

    private ImageView backImg;

    private LoginCodePresenter loginPresenter;

    private String phone;

    @Override protected int setLayoutId() {
        return R.layout.activity_login_code;
    }

    @Override protected void initView() {
        getIntentData();
        tipsTv = findViewById(R.id.login_top_tips_tv);
        codeEt = findViewById(R.id.login_ed);
        retryTv = findViewById(R.id.login_code_time_tv);
        backImg = findViewById(R.id.left_back_iv);

        tipsTv.setText(String.format(getString(R.string.login_input_code_tips),phone));
        mTimer = new TimeCount(60000, 1000);

        timeStart();
    }

    private void getIntentData(){
        phone = getIntent().getStringExtra("phone");
    }

    @Override protected void initData(@Nullable Bundle savedInstanceState) {
        loginPresenter = (LoginCodePresenter) getMvpPresenter();
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


        codeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override public void afterTextChanged(Editable s) {
                if (s.length()!=0 && s.length() == 4){
                    String code = codeEt.getText().toString();
                    loginPresenter.checkCode(phone,code);
                }
            }
        });

        RxView.clicks(retryTv)
            .throttleFirst(500,TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    loginPresenter.getCode(phone);
                    timeStart();
                }
            });

        RxView.clicks(backImg)
            .throttleFirst(500,TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    finish();
                }
            });
    }
    /**
     * 计时器
     */
    private TimeCount mTimer;
    /**
     * 计数器剩余的时间
     */
    private long lastTime = 60000;

    /**
     * 启动计时器
     */
    private void timeStart() {
        mTimer.start();
        retryTv.setClickable(false);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            retryTv.setTextColor(ContextCompat.getColor(LoginCodeActivity.this,R.color.color_blue));
            retryTv.setText(getString(R.string.login_input_code_retry));
            retryTv.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            retryTv.setClickable(false);//防止重复点击
            retryTv.setTextColor(ContextCompat.getColor(LoginCodeActivity.this,R.color.color_cccccc));
            String timeStr = String.format(getString(R.string.login_input_code_time), String.valueOf(millisUntilFinished / 1000));
            lastTime = millisUntilFinished;
            retryTv.setText(timeStr);
        }
    }

    @Override public void goMain(LoginCodeModel model) {
        AppPreferencesHelper.put(Constant.TOKEN,model.getToken());
        AppPreferencesHelper.put(Constant.UID,model.getId());
        AppPreferencesHelper.put(Constant.U_IMAGE_URL,model.getHead_img());
        AppPreferencesHelper.put(Constant.USER_NAME,model.getName());
        AppPreferencesHelper.put(Constant.USER_PHONE,model.getPhone());
        AppPreferencesHelper.put(Constant.USER_SPACE,Integer.parseInt(model.getUse_space()));
        AppPreferencesHelper.put(Constant.TOTAL_SPACE,Integer.parseInt(model.getTotal_space()));
        ToastUtils.showIconToast(this,"登录成功");
        startActivity(new Intent(LoginCodeActivity.this,MainActivity.class));
        mTimer.onFinish();
        mTimer.cancel();
        finish();
    }

    @Override public void getCode() {

    }

    @Override public void goError() {
        mTimer.onFinish();
        mTimer.cancel();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.onFinish();
        mTimer.cancel();
    }
}
