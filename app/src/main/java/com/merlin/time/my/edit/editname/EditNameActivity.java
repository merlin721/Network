package com.merlin.time.my.edit.editname;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.common.CommonUtils;
import com.merlin.time.entity.BaseMode;
import com.merlin.time.my.feedback.FeedbackActivity;
import com.merlin.time.my.feedback.model.FeedbackBaseModel;
import com.merlin.time.network.AppNetWorkHelper;
import com.merlin.time.view.TimeTextView;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.ToastUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * @author merlin
 * @date 2018年11月11日
 * desc 编辑姓名
 */
public class EditNameActivity extends BaseActivity {
    //http://time.taowangzhan.com/api/user/modify-user-info?token=v_ee0cf3b68b1ea084e4a344e529ba5722_1_1517035627&name=123&head_img=1111
    private ImageView back;
    private EditText nameEt;
    private TimeTextView commitTv;

    @Override protected int setLayoutId() {
        return R.layout.activity_edit_name;
    }

    @Override protected void initView() {
        back = findViewById(R.id.edit_name_back_img);
        nameEt = findViewById(R.id.edit_name_input);
        commitTv= findViewById(R.id.edit_name_edit_btn);
    }

    @Override protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
            .statusBarDarkFont(true, 0.2f)
            .fitsSystemWindows(true)
            .keyboardEnable(true,WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            .statusBarColor(com.soyoung.component_base.R.color.ffffff)
            .navigationBarWithKitkatEnable(false)
            .init();
    }

    @Override protected void initData(@Nullable Bundle savedInstanceState) {
        nameEt.setText(AppPreferencesHelper.getString(Constant.USER_NAME));
    }

    @Override protected void setListener() {
        Disposable disposable = RxView.clicks(back)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    finish();
                }
            });
        Disposable disposable1 = RxView.clicks(commitTv)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    commit();
                }
            });
        getCompositeDisposable().add(disposable);
    }


    private void commit(){
        String name = nameEt.getText().toString();
        Disposable disposable = AppNetWorkHelper.getInstance().editName(name)
            .compose(toMain())
            .subscribe(jsonObject -> {
                Gson gson = new Gson();
                BaseMode model = gson.fromJson(jsonObject.toString(),BaseMode.class);
                //名字修改成功发送event告诉 我的界面和修改个人信息界面让他们修改名字
                if (!TextUtils.isEmpty(model.ret) && "1".equals(model.ret)){
                    AppPreferencesHelper.put(Constant.USER_NAME,name);
                    Message message  = new Message();
                    message.what = CommonUtils.EVENT_CHANGE_NAME_CODE;
                    message.obj = name;
                    ToastUtils.showToast(EditNameActivity.this,"修改成功");
                    finish();
                    EventBus.getDefault().post(message);
                }else {
                    ToastUtils.showToast(EditNameActivity.this,model.error.getMsg());
                }
            }, throwable -> throwable.printStackTrace());
        getCompositeDisposable().add(disposable);
    }
}
