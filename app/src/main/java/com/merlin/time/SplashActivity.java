package com.merlin.time;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.merlin.time.common.CommonUtils;
import com.merlin.time.entity.BaseMode;
import com.merlin.time.login.LoginActivity;
import com.merlin.time.login.model.LoginCodeModel;
import com.merlin.time.main.MainActivity;
import com.merlin.time.network.AppNetWorkHelper;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.ToastUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.json.JSONObject;

/**
 * @author merlin
 * @date 2018年11月11日
 * desc 首页
 */
public class SplashActivity extends BaseActivity {

  private CountDownTimer countDownTimer = new CountDownTimer(2200, 1000) {
    @Override
    public void onTick(long l) {
      //mBtnJump.setText("跳过广告"+ l/1000 + "s" );
    }

    @Override
    public void onFinish() {
      //mBtnJump.setText("跳过广告"+0+"s");
      toMainActivity();
    }
  };

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
  }

  @Override protected int setLayoutId() {
    return R.layout.activity_splash;
  }

  @Override protected void initView() {

  }

  @Override protected void initData(@Nullable Bundle savedInstanceState) {
    countDownTimer.start();
  }

  @Override protected void setListener() {

  }

  private void toMainActivity() {
    String token = AppPreferencesHelper.getString(Constant.TOKEN);
    if (TextUtils.isEmpty(token)) {
      startActivity(new Intent(SplashActivity.this, LoginActivity.class));
      finish();
    } else {
      checkToken(token);
    }
  }

  private void checkToken(String token) {
    Disposable disposable = AppNetWorkHelper
        .getInstance()
        .checkAccess(token)
        .compose(toMain())
        .subscribe(new Consumer<JSONObject>() {
          @Override public void accept(JSONObject jsonObject) throws Exception {
            Gson gson = new Gson();
            BaseMode baseMode = gson.fromJson(jsonObject.toString(), BaseMode.class);
            if (baseMode.ret.equals("1")) {
              String data = jsonObject.optString("data");
              if (!TextUtils.isEmpty(data)) {
                Gson gson1 = new Gson();
                LoginCodeModel model = gson1.fromJson(data, LoginCodeModel.class);

                //AppPreferencesHelper.put(Constant.TOKEN, model.getToken());
                AppPreferencesHelper.put(Constant.UID, model.getId());
                AppPreferencesHelper.put(Constant.U_IMAGE_URL, model.getHead_img());
                AppPreferencesHelper.put(Constant.USER_NAME, model.getName());
                AppPreferencesHelper.put(Constant.USER_PHONE, model.getPhone());
                AppPreferencesHelper.put(Constant.USER_SPACE,
                    Integer.parseInt(model.getUse_space()) );
                AppPreferencesHelper.put(Constant.TOTAL_SPACE,
                    Integer.parseInt(model.getTotal_space()) );
                AppPreferencesHelper.put(Constant.USED_TOTAL_DESC, model.getUsed_total_desc());
                AppPreferencesHelper.put(Constant.USED_RATE, model.getUsed_rate());
                AppPreferencesHelper.put(Constant.IS_SIGN_IN, model.getIs_sign_in());
              }
              startActivity(new Intent(SplashActivity.this, MainActivity.class));
              finish();
            } else {
              if ("100".equals(baseMode.error.getCode())) {
                AppPreferencesHelper.clear();
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
              }
              ToastUtils.showToast(SplashActivity.this, baseMode.error.getMsg());
            }
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            throwable.printStackTrace();
          }
        });
    getCompositeDisposable().add(disposable);
  }
}
