package com.merlin.time.sharegallery.gallerylist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.my.edit.model.UploadPicBaseBean;
import com.merlin.time.sharegallery.galleryitem.GalleryItemActivity;
import com.merlin.time.sharegallery.gallerylist.model.GalleryCallbackParamsModel;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListCallBackModel;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListItemModel;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListModel;
import com.merlin.time.sharegallery.gallerylist.presenter.GalleryListPresenter;
import com.merlin.time.sharegallery.gallerylist.view.GalleryListAdapter;
import com.merlin.time.sharegallery.gallerylist.view.GalleryListView;
import com.merlin.time.sharegallery.model.ShareGalleryModel;
import com.merlin.time.utils.ExcutorPoolUtils;
import com.merlin.time.utils.Glide4Engine;
import com.merlin.time.utils.SystemUtils;
import com.merlin.time.utils.WXUtils;
import com.merlin.time.view.TimeTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.mvp.factory.CreatePresenter;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.LogUtils;
import com.soyoung.component_base.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author merlin
 * @date 2018年11月11日
 * desc 照片列表
 */
// TODO: 2019/1/13 视频
@CreatePresenter(GalleryListPresenter.class)
public class GalleryListActivity extends BaseActivity implements GalleryListView {
    private ImageView back;
    private TimeTextView titleTv;
    private ImageView rightImg;
    private RecyclerView recyclerView;
    private TimeTextView uploadTv;

    private SmartRefreshLayout refreshLayout;
    private GalleryListPresenter presenter;
    private TimeTextView progressTv;
    private RelativeLayout progressRl;

    private GalleryListAdapter adapter;

    private String albumId;
    private int page = 1;
    private int size = 40;
    ShareGalleryModel shareGalleryModel;
    List<String> pics = new ArrayList<>();
    /**
     * 当前上传的是第几张图片
     */
    int currentNum = 0;
    //总共上传几张图片
    int totalSize = 0;

    private OSSAsyncTask task;

    public OSS oss;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_gallery_list;
    }

    @Override
    protected void initView() {
        albumId = getIntent().getStringExtra("album_id");
        shareGalleryModel = (ShareGalleryModel) getIntent().getSerializableExtra("model");
        width = SystemUtils.getDisplayWidth(this);
        back = findViewById(R.id.gallery_list_title_left);
        titleTv = findViewById(R.id.gallery_list_title);
        rightImg = findViewById(R.id.gallery_list_right);
        recyclerView = findViewById(R.id.gallery_list_recycle);
        uploadTv = findViewById(R.id.gallery_list_upload);
        refreshLayout = findViewById(R.id.share_refreshLayout);
        progressTv = findViewById(R.id.gal_progress_tv);
        progressRl = findViewById(R.id.gal_progress_rl);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(GalleryListActivity.this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new GalleryListAdapter(this);
        recyclerView.setAdapter(adapter);
        initHeader();
    }

    private void initHeader(){
        View view = LayoutInflater.from(this).inflate(R.layout.share_list_header,null );
        TimeTextView share = view.findViewById(R.id.share_list_share_tv);
        TimeTextView num = view.findViewById(R.id.share_list_num_tv);
        num.setText("共"+shareGalleryModel.getUser_num()+"位相册成员");
        RxView.clicks(share)
            .throttleFirst(500,TimeUnit.MILLISECONDS )
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    share();
                }
            });
        adapter.addHeaderView(view);

    }
    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

        presenter = (GalleryListPresenter) getMvpPresenter();
        presenter.getData(albumId, page, size);
        presenter.getCallBack();
        titleTv.setText(getIntent().getStringExtra("name"));
        initBitmap();

    }
    private Bitmap bitmap = null;
    private int width;
    private void initBitmap() {

        ExcutorPoolUtils.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    bitmap = Glide.with(context)
                        .asBitmap()
                        .load(shareGalleryModel.getCover_img())
                        .submit(width, 500)
                        .get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void share(){
        if (WXUtils.api.isWXAppInstalled()) {
            WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
            miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
            miniProgramObj.miniprogramType =
                WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
            miniProgramObj.userName = "gh_e669aa61f016";     // 小程序原始id
            String path  = "/pages/landing/index?albumid=%1$s&username=%2$s&albumname=%3$s&invitation_code=%4$s&n=%5$s&img=%6$s";
            miniProgramObj.path = String.format(path,shareGalleryModel.getId(),AppPreferencesHelper.getString(Constant.USER_NAME),shareGalleryModel.getName(),shareGalleryModel.getInvitation_code(),shareGalleryModel.getImg_num(),shareGalleryModel.getCover_img());            //小程序页面路径
            WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
            msg.title = shareGalleryModel.getName();                    // 小程序消息title
            msg.description = shareGalleryModel.getName();               // 小程序消息desc

            Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            msg.thumbData = Bitmap2Bytes(sendBitmap);                      // 小程序消息封面图片，小于128k

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = "miniProgram";
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
            WXUtils.api.sendReq(req);
        } else {
            ToastUtils.showToast(this, "请先安装微信");
        }
    }
    /**
     * 微信分享bitmap转化成byte
     */
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    private void initOss() {
        String endpoint = "oss-cn-beijing.aliyuncs.com";

        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog(); //这个开启会支持写入手机sd卡中的一份日志文件位置在SDCard_path\OSSLog\logs.csv

        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(callBackModel.token_info.getAccessKeyId(), callBackModel.token_info.getAccessKeySecret(), callBackModel.token_info.getSecurityToken());
        oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
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
        Disposable disposable2 = RxView.clicks(rightImg)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        goGalleryMember();
                    }
                });
        Disposable disposable1 = RxView.clicks(uploadTv)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        commit();
                    }
                });
        getCompositeDisposable().add(disposable);
        getCompositeDisposable().add(disposable1);
        getCompositeDisposable().add(disposable2);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (contentData != null) {
                    Intent intent = new Intent(GalleryListActivity.this, GalleryItemActivity.class);
                    intent.putExtra("model", list.get(i));
                    intent.putExtra("position", i);
                    startActivity(intent);
                }
            }
        });
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.getData(albumId, page, size);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.getData(albumId, page, size);
            }
        });
    }

    /**
     * 跳转到相册成员界面
     */
    private void goGalleryMember() {

    }

    private void openMatisse() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        Matisse.from(GalleryListActivity.this)
                                .choose(MimeType.ofAll(), false)
                                .countable(true)
                                .capture(true)
                                .captureStrategy(
                                        new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider", "test"))
                                .maxSelectable(9)
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
                                .originalEnable(true)
                                .setOnCheckedListener(new OnCheckedListener() {
                                    @Override
                                    public void onCheck(boolean isChecked) {
                                        // DO SOMETHING IMMEDIATELY HERE
                                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                                    }
                                })
                                .forResult(REQUEST_CODE_CHOOSE);

                    } else {

                    }
                });

    }

    private static final int REQUEST_CODE_CHOOSE = 10;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            pics.addAll(Matisse.obtainPathResult(data));
            totalSize = Matisse.obtainPathResult(data).size();
            currentNum = 0;

            upload(Matisse.obtainPathResult(data).get(currentNum));
        }
    }


    /**
     * 跳转到上传图片界面。
     */
    private void commit() {
        //ApiUrl.uploadImg;
        openMatisse();
    }


    /**
     * 上传图片
     *
     * @param path 图片路径
     */
    private void upload(String path) {
        currentNum++;
        String pic = path.substring(path.lastIndexOf("/") + 1);
        LogUtils.d(pic);
        PutObjectRequest request = new PutObjectRequest("timebottle", callBackModel.dir + pic, path);
        String str = new String(Base64.decode(callBackModel.callback, Base64.DEFAULT));
        LogUtils.d(str);
        GalleryCallbackParamsModel callbackParamsModel = new Gson().fromJson(str, GalleryCallbackParamsModel.class);
        // 异步上传时可以设置进度回调。
        request.setProgressCallback((request1, currentSize, totalSize) -> runOnUiThread(() -> {
            progressRl.setVisibility(View.VISIBLE);
            progressTv.setText("上传第" + (currentNum) + "张图片，进度" + currentSize * 100 / totalSize + "%");

        }));
        request.setCallbackParam(new HashMap<String, String>() {{
            LogUtils.d(callbackParamsModel.getCallbackUrl());
            put("callbackUrl", callbackParamsModel.getCallbackUrl());
            put("callbackHost", callBackModel.callback_host);
            put("callbackBodyType", callbackParamsModel.getCallbackBodyType());
            put("callbackBody", callbackParamsModel.getCallbackBody() + albumId);
        }});

        task = oss.asyncPutObject(request, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                runOnUiThread(() -> progressRl.setVisibility(View.GONE));
                // 只有设置了servercallback，这个值才有数据
                String serverCallbackReturnJson = result.getServerCallbackReturnBody();

                if (currentNum < totalSize) {
                    upload(pics.get(currentNum));
                } else {
                    Gson gson = new Gson();
                    UploadPicBaseBean model = gson.fromJson(serverCallbackReturnJson, UploadPicBaseBean.class);
                    LogUtils.d(model.ret);
                    if (model.ret.equals("1") && null != model.getData()) {

                        presenter.getData(albumId, page, size);
                    }
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常
                runOnUiThread(() -> progressRl.setVisibility(View.GONE));
                if (clientException != null) {
                    // 本地异常如网络异常等
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });


    }

    GalleryListModel contentData ;
    List<GalleryListItemModel> list = new ArrayList<>();

    @Override
    public void showData(GalleryListModel model) {
        if (model != null && model.getRows() != null && model.getRows().size() > 0) {
            contentData = model;

            if (page == 1) {
                list.clear();
                list.addAll(model.getRows());
                adapter.setNewData(model.getRows());
            } else {
                list.addAll(model.getRows());
                adapter.addData(model.getRows());
            }
        }

        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (model.getPage() >= model.getTotal_page()){
            refreshLayout.finishLoadMoreWithNoMoreData();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null)
            task.cancel();
    }

    @Override
    public void setCallbackParams(GalleryListCallBackModel model) {
        this.callBackModel = model;
        initOss();
    }

    GalleryListCallBackModel callBackModel;
}
