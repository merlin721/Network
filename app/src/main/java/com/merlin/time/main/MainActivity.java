package com.merlin.time.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.merlin.time.R;
import com.merlin.time.SplashActivity;
import com.merlin.time.entity.BaseMode;
import com.merlin.time.login.LoginActivity;
import com.merlin.time.login.model.LoginCodeModel;
import com.merlin.time.main.MainFragment;
import com.merlin.time.my.MyCenterFragment;
import com.merlin.time.mygallery.MyGalleryFragment;
import com.merlin.time.network.AppNetWorkHelper;
import com.merlin.time.sharegallery.ShareGalleryFragment;
import com.soyoung.component_base.AppManager;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lib.NetworkRequestUtil;

/**
 * @author merlin
 * @date 2018年11月11日
 * desc 首页
 */
public class MainActivity extends BaseActivity {

    public RadioGroup group;

    private FragmentManager supportFragmentManager;

    private FragmentTransaction fragmentTransaction;

    private MainFragment mainFragment;

    private MyGalleryFragment myGalleryFragment;

    private ShareGalleryFragment shareGalleryFragment;

    private MyCenterFragment myCenterFragment;

    @Override protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override protected void initView() {
        setSwipeBackEnable(false);
        group = findViewById(R.id.main_rg);
    }

    @Override protected void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mainFragment = new MainFragment();
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment, mainFragment);
        fragmentTransaction.commit();
    }

    @Override protected void setListener() {
        group.setOnCheckedChangeListener((group, checkedId) -> {
            fragmentTransaction = supportFragmentManager.beginTransaction();
            hideFragment();
            switch (checkedId) {
                case R.id.rb_home:
                    if (mainFragment == null) {
                        mainFragment = new MainFragment();
                        fragmentTransaction.add(R.id.main_fragment, mainFragment);
                    } else {
                        fragmentTransaction.show(mainFragment);
                    }
                    break;
                case R.id.rb_my_photo:
                    if (myGalleryFragment == null) {
                        myGalleryFragment = new MyGalleryFragment();
                        fragmentTransaction.add(R.id.main_fragment, myGalleryFragment);
                    } else {
                        fragmentTransaction.show(myGalleryFragment);
                    }

                    break;
                case R.id.rb_share_photo:
                    if (shareGalleryFragment == null) {
                        shareGalleryFragment = new ShareGalleryFragment();
                        fragmentTransaction.add(R.id.main_fragment, shareGalleryFragment);
                    } else {
                        fragmentTransaction.show(shareGalleryFragment);
                    }
                    break;
                case R.id.rb_my_center:
                    if (myCenterFragment == null) {
                        myCenterFragment = new MyCenterFragment();
                        fragmentTransaction.add(R.id.main_fragment, myCenterFragment);
                    } else {
                        fragmentTransaction.show(myCenterFragment);
                    }
                    break;
                default:
                    break;
            }

            fragmentTransaction.commitAllowingStateLoss();
        });
    }

    private void hideFragment() {
        if (mainFragment != null) {
            fragmentTransaction.hide(mainFragment);
        }
        if (myGalleryFragment != null) {
            fragmentTransaction.hide(myGalleryFragment);
        }
        if (shareGalleryFragment != null) {
            fragmentTransaction.hide(shareGalleryFragment);
        }
        if (myCenterFragment != null) {
            fragmentTransaction.hide(myCenterFragment);
        }
    }

    long mExitTime;


    @Override protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override protected boolean isContainFragment() {
        return true;
    }


    private void getShareInfo(){
        //http://time.taowangzhan.com/api/album/get-album-info?id=1&token=v_b89af95f23ab8853a5d39fb698ba6a28_293_1547973441
        Disposable disposable = AppNetWorkHelper
                .getInstance()
                .getShareInfo()
                .compose(toMain())
                .subscribe(new Consumer<JSONObject>() {
                    @Override public void accept(JSONObject jsonObject) throws Exception {
                        Gson gson = new Gson();
                        BaseMode baseMode = gson.fromJson(jsonObject.toString(), BaseMode.class);

                    }
                }, new Consumer<Throwable>() {
                    @Override public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
        getCompositeDisposable().add(disposable);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showMessage(context.getString(R.string.click_back));
                mExitTime = System.currentTimeMillis();
            } else {
                NetworkRequestUtil.getInstance().stopService(getApplicationContext());

                AppManager.getAppManager().AppExit(context);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
