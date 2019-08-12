package com.merlin.time.sharegallery.create;

import android.content.Intent;
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
import com.merlin.time.network.AppNetWorkHelper;
import com.merlin.time.sharegallery.gallerylist.GalleryListActivity;
import com.merlin.time.view.TimeTextView;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.ToastUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

/**
 * @author merlin
 * @date 2018年11月11日
 * desc 创建相册
 */
public class CreateGalleryActivity extends BaseActivity {
    private ImageView back;
    private EditText nameEt;
    private TimeTextView commitTv;

    @Override protected int setLayoutId() {
        return R.layout.activity_create_gallery;
    }

    @Override protected void initView() {
        back = findViewById(R.id.create_gal_back_img);
        nameEt = findViewById(R.id.create_gal_input);
        commitTv= findViewById(R.id.create_gal_edit_btn);
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
        Disposable disposable = AppNetWorkHelper.getInstance().createGallery(name)
            .compose(toMain())
            .subscribe(jsonObject -> {
                Gson gson = new Gson();
                CreateGalBaseBean model = gson.fromJson(jsonObject.toString(),CreateGalBaseBean.class);
                //名字修改成功发送event告诉 我的界面和修改个人信息界面让他们修改名字
                if (!TextUtils.isEmpty(model.ret) && "1".equals(model.ret)){
                    ToastUtils.showToast(CreateGalleryActivity.this,getString(R.string.create_gallery));
                    Intent intent = new Intent(CreateGalleryActivity.this,GalleryListActivity.class);
                    intent.putExtra("album_id",model.data.getId());
                    intent.putExtra("name",model.data.getName());
                    intent.putExtra("model", model.data);
                    startActivity(intent);
                    finish();
                }else {
                    ToastUtils.showToast(CreateGalleryActivity.this,model.error.getMsg());
                }
            }, Throwable::printStackTrace);
        getCompositeDisposable().add(disposable);
    }
}
