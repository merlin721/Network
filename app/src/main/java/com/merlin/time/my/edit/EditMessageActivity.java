package com.merlin.time.my.edit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.network.CallBack;
import com.merlin.network.NetworkDelegate;
import com.merlin.time.R;
import com.merlin.time.common.CommonUtils;
import com.merlin.time.my.edit.editname.EditNameActivity;
import com.merlin.time.my.edit.model.UploadPicBaseBean;
import com.merlin.time.network.ApiUrl;
import com.merlin.time.sharegallery.gallerylist.GalleryListActivity;
import com.merlin.time.utils.Glide4Engine;
import com.merlin.time.utils.SystemUtils;
import com.merlin.time.view.TimeTextView;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.image.GlideApp;
import com.soyoung.component_base.image.ImageWorker;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author merlin
 * @date 2018年11月11日
 * desc 关于
 */
public class EditMessageActivity extends BaseActivity {
    private ImageView back;

    private ImageView headImg;
    private TimeTextView nameTv;
    private RelativeLayout editNameRl;

    @Override protected int setLayoutId() {
        return R.layout.activity_edit;
    }

    @Override protected void initView() {
        back = findViewById(R.id.about_back_img);
        headImg = findViewById(R.id.edit_head_img);
        nameTv = findViewById(R.id.edit_name_tv);
        editNameRl = findViewById(R.id.edit_name_rl);
    }

    @Override protected void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        ImageWorker.loadImageCircle(context, AppPreferencesHelper.getString(Constant.U_IMAGE_URL),
            headImg, R.drawable.my_img_head);
        nameTv.setText(AppPreferencesHelper.getString(Constant.USER_NAME));
    }

    @Override protected void setListener() {
        Disposable disposable = RxView.clicks(back)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    finish();
                }
            });

        Disposable disposable1 = RxView.clicks(headImg)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    editPhoto();
                }
            });

        Disposable disposable2 = RxView.clicks(editNameRl)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    goEditName();
                }
            });
        getCompositeDisposable().add(disposable);
    }

    @Override protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBus(Message message) {
        if (message.what == CommonUtils.EVENT_CHANGE_NAME_CODE) {
            nameTv.setText((String) message.obj);
        }
    }

    /**
     * 修改头像
     */
    private void editPhoto() {
        //PickPhotoDialog.Builder builder = new PickPhotoDialog.Builder(EditMessageActivity.this);
        //PickPhotoDialog dialog = builder.create(1);
        //dialog.show();
        openMatisse();
    }



    private void goEditName() {
        startActivity(new Intent(EditMessageActivity.this, EditNameActivity.class));
    }


    private void upload(String path) {
        File protraitFile = new File(path);
        if ("Meizu".equals(android.os.Build.MANUFACTURER)) {
            ImageWorker.imageResizeLoader(context, protraitFile, headImg, new CircleCrop(),
                SystemUtils
                    .dip2px(context, 70), SystemUtils.dip2px(context, 70));
        } else {
            runOnUiThread(
                () -> GlideApp.with(EditMessageActivity.this)
                    .load(new File(path))
                    .transform(new CircleCrop())
                    .centerCrop()
                        .transform(new CircleCrop())
                    .into(headImg));
        }
        final HashMap<String, String> params = new HashMap<>();
        params.put("token", AppPreferencesHelper.getString(Constant.TOKEN));
        NetworkDelegate.getInstance()
            .uploadPost(String.class, ApiUrl.uploadHead, params, new CallBack<String>() {
                @Override
                public void onResponse(String response) {

                    Gson gson = new Gson();
                    UploadPicBaseBean model = gson.fromJson(response, UploadPicBaseBean.class);
                    if (model.ret.equals("1")) {
                        if (model.getData() != null) {
                            if (!TextUtils.isEmpty(model.getData().getIs_upload()) && "1".equals(
                                model.getData().getIs_upload())) {
                                AppPreferencesHelper.put(Constant.U_IMAGE_URL,
                                    model.getData().getHead_img());
                                Message message = new Message();
                                message.what = CommonUtils.EVENT_CHANGE_PIC_CODE;
                                message.obj = model.getData().getHead_img();
                                EventBus.getDefault().post(message);
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Exception exception) {
                    //hideLoading();
                }
            }, path,0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            upload(Matisse.obtainPathResult(data).get(0));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
        int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
    private void openMatisse(){
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe(aBoolean -> {
                if (aBoolean){
                    Matisse.from(EditMessageActivity.this)
                        .choose(MimeType.ofAll(), false)
                        .countable(true)
                        .capture(true)
                        .captureStrategy(
                            new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider","test"))
                        .maxSelectable(1)
                        //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(
                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .thumbnailScale(0.85f)
                        //                                            .imageEngine(new GlideEngine())  // for glide-V3
                        .imageEngine(new Glide4Engine())    // for glide-V4
                        .setOnSelectedListener(new OnSelectedListener() {
                            @Override
                            public void onSelected(
                                @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                // DO SOMETHING IMMEDIATELY HERE
                                Log.e("onSelected", "onSelected: pathList=" + pathList);

                            }
                        })
                        .originalEnable(true)
                        .maxOriginalSize(10)
                        .autoHideToolbarOnSingleTap(true)
                        .setOnCheckedListener(new OnCheckedListener() {
                            @Override
                            public void onCheck(boolean isChecked) {
                                // DO SOMETHING IMMEDIATELY HERE
                                Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                            }
                        })
                        .forResult(REQUEST_CODE_CHOOSE);

                }else {

                }
            });

        //Matisse
        //    .from(ShareGalleryFragment.this)
        //    .choose(MimeType.ofAll())
        //    .countable(true)
        //    .maxSelectable(9)
        //    //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
        //    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
        //    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
        //    .thumbnailScale(0.85f)
        //    .imageEngine(new GlideEngine())
        //    .forResult(REQUEST_CODE_CHOOSE);
    }
    private static final int REQUEST_CODE_CHOOSE = 11;
}
