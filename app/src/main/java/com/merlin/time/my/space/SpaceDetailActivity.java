package com.merlin.time.my.space;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.my.feedback.model.FeedbackListBaseBean;
import com.merlin.time.network.AppNetWorkHelper;
import com.merlin.time.utils.ExcutorPoolUtils;
import com.merlin.time.utils.SystemUtils;
import com.merlin.time.utils.Util;
import com.merlin.time.utils.WXUtils;
import com.merlin.time.view.TimeTextView;
import com.soyoung.component_base.image.ImageWorker;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.FileUtils;
import com.soyoung.component_base.util.ToastUtils;
import com.soyoung.component_base.util.Utils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/**
 * @author merlin
 * @date 2019、3、30
 * desc 空间明细
 */
public class SpaceDetailActivity extends BaseActivity {

  ImageView back;

  private RecyclerView recyclerView;
  private SpaceDetailAdapter adapter;

  @Override protected int setLayoutId() {
    return R.layout.activity_space_detail;
  }

  @Override protected void initView() {
    back = findViewById(R.id.about_back_img);
    recyclerView = findViewById(R.id.space_recycler);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);
    adapter = new SpaceDetailAdapter(this);
    recyclerView.setAdapter(adapter);
    initBottom();
  }

  @Override protected void initData(@Nullable Bundle savedInstanceState) {
    initData();
  }

  private void initBottom(){
    View footer = LayoutInflater.from(this).inflate(R.layout.space_detail_bottom,null );
    View empty = LayoutInflater.from(this).inflate(R.layout.space_empty_bottom,null );
    adapter.setFooterView(footer);
    adapter.setEmptyView(empty);
  }
  int index = 1;

  private void initData() {
    Disposable disposable = AppNetWorkHelper
        .getInstance()
        .getSpaceDetail()
        .compose(toMain())
        .subscribe(new Consumer<JSONObject>() {
          @Override
          public void accept(JSONObject jsonObject) throws Exception {
            SpaceDetailBaseBean
                model = new Gson().fromJson(jsonObject.toString(), SpaceDetailBaseBean.class);
            if (model != null && "1".equals(model.ret)) {
              adapter.setNewData(model.getData());
              adapter.notifyDataSetChanged();
            } else {
              ToastUtils.showToast(context, model.error.getMsg());
            }
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {

          }
        });
    getCompositeDisposable().add(disposable);
  }

  @Override protected void setListener() {
    Disposable disposable = RxView.clicks(back)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            finish();
          }
        });
  }
}
