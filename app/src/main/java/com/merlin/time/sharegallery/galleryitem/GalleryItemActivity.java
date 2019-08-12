package com.merlin.time.sharegallery.galleryitem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.jzvd.JzvdStd;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.entity.BaseMode;
import com.merlin.time.network.AppNetWorkHelper;
import com.merlin.time.sharegallery.galleryitem.model.GalleryItem;
import com.merlin.time.sharegallery.galleryitem.model.GalleryItemBean;
import com.merlin.time.sharegallery.galleryitem.presenter.GalleryItemPresenter;
import com.merlin.time.sharegallery.galleryitem.view.GalleryItemView;
import com.merlin.time.sharegallery.gallerylist.GalleryListActivity;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListItemModel;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListModel;
import com.merlin.time.sharegallery.model.ShareGalleryBaseModel;
import com.merlin.time.utils.SystemUtils;
import com.merlin.time.view.TimeTextView;
import com.soyoung.component_base.image.ImageWorker;
import com.soyoung.component_base.mvp.factory.CreatePresenter;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.LogUtils;
import com.soyoung.component_base.util.ToastUtils;

import cn.jzvd.Jzvd;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/**
 * @author merlin
 * @date 2018年11月11日
 * desc 照片详情
 */
@CreatePresenter(GalleryItemPresenter.class)
public class GalleryItemActivity extends BaseActivity implements GalleryItemView {

    private ImageView back;
    private RecyclerView recyclerView;
    private TimeTextView timeTextView;
    private TimeTextView nameTv;
    private ImageView delImg;
    //private ViewPager viewPager;

    private GalleryDetailAdapter adapter;

    //private GalleryDetailPageAdapter pageAdapter;

    private GalleryListItemModel model;

    GalleryItemPresenter presenter;

    private RelativeLayout relativeLayout;

    private int positon;
    int page = 1;
    View header;

    @Override protected int setLayoutId() {
        return R.layout.activity_gallery_detail;
    }

    @Override protected void initView() {
        model = (GalleryListItemModel) getIntent().getExtras().get("model");
        back = findViewById(R.id.gallery_detail_title_left);
        recyclerView = findViewById(R.id.gallery_detail_recycle);
        adapter = new GalleryDetailAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        relativeLayout = findViewById(R.id.gallery_detail_title_left_rl);
        header = initHeader();
        adapter.addHeaderView(header);
    }

    //@Override
    //protected void initImmersionBar() {
    //    mImmersionBar = ImmersionBar.with(this);
    //    mImmersionBar
    //        .statusBarDarkFont(true, 0.2f)
    //        .navigationBarWithKitkatEnable(false)
    //        .init();
    //}
    private View initHeader() {
        View rootView = LayoutInflater.from(GalleryItemActivity.this)
            .inflate(R.layout.gallery_deital_header, null);
        timeTextView = rootView.findViewById(R.id.gal_detail_header_time);
        nameTv = rootView.findViewById(R.id.gal_detail_header_name);
        delImg = rootView.findViewById(R.id.gal_detail_header_del);
        //viewPager = view.findViewById(R.id.gallery_detail_header_view_pager);
        //pageAdapter = new GalleryDetailPageAdapter(this);
        //viewPager.setAdapter(pageAdapter);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT );
        layoutParams.height = SystemUtils.getDisplayHeight(this) - SystemUtils.dip2px(this, 120);
        //viewPager.setLayoutParams(layoutParams);
        ImageView imageView = rootView.findViewById(R.id.gallery_detail_header_photo_view);
        JzvdStd playerStandard = rootView.findViewById(R.id.videoplayer);
        if (model != null) {
            if ("1".equals(model.getType())){
                imageView.setLayoutParams(layoutParams);
                imageView.setVisibility(View.VISIBLE);
                playerStandard.setVisibility(View.GONE);
                ImageWorker.loadImage(this, model.getImg_url_big(), imageView);
            }else if ("2".equals(model.getType())){
                imageView.setVisibility(View.GONE);
                playerStandard.setVisibility(View.VISIBLE);
                playerStandard.setLayoutParams(layoutParams);
                String url = model.getVideo_url();
                if (url.contains("https")){
                    url = url.replace("https","http");
                }
                LogUtils.e(url);
                playerStandard.setUp(url, "时间视频",Jzvd.SCREEN_WINDOW_NORMAL);
                ImageWorker.loadImage(this,model.getImg_url_big(),playerStandard.thumbImageView);
                playerStandard.startButton.performClick();
            }
        }
        return rootView;
    }

    @Override protected void initData(@Nullable Bundle savedInstanceState) {

        presenter = (GalleryItemPresenter) getMvpPresenter();
        model = (GalleryListItemModel) getIntent().getExtras().get("model");

        positon = getIntent().getIntExtra("position", 0);
        mId = model.getId();
        presenter.getData(mId, page);
        if (model != null ) {
            resultText(model);

        }
    }

    private void resultText(GalleryListItemModel itemModel) {
        StringBuilder builder = new StringBuilder(itemModel.getImg_time());
        builder
            .append(" ")
            .append(itemModel.getImg_time_alias());
        timeTextView.setText(builder.toString());
        if (TextUtils.isEmpty(itemModel.getImg_address())) {
            nameTv.setText(itemModel.getImg_user_name());
        } else {
            nameTv.setText(itemModel.getImg_address() + " | " + itemModel.getImg_user_name());
        }
    }

    @Override protected void setListener() {
        Disposable disposable = RxView.clicks(relativeLayout)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                    finish();
                }
            });
        Disposable disposable1 = RxView.clicks(delImg)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe(new Consumer<Object>() {
                @Override public void accept(Object o) throws Exception {
                   deletePic();
                }
            });

        //viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        //    @Override public void onPageScrolled(int i, float v, int i1) {
        //
        //    }
        //
        //    @Override public void onPageSelected(int i) {
        //        resultText(model.getRows().get(i));
        //        mId = model.getRows().get(i).getId();
        //        presenter.getData(model.getRows().get(i).getId(), page);
        //    }
        //
        //    @Override public void onPageScrollStateChanged(int i) {
        //
        //    }
        //});
    }

    private String mId;


    private void deletePic(){
        Disposable disposable = AppNetWorkHelper.getInstance()
            .deletePic(mId)
            .compose(toMain())
            .subscribe(new Consumer<JSONObject>() {
                @Override public void accept(JSONObject jsonObject) throws Exception {
                    BaseMode model =
                        new Gson().fromJson(jsonObject.toString(), BaseMode.class);
                    if (!TextUtils.isEmpty(model.ret) & "1".equals(model.ret)) {
                        ToastUtils.showToast(GalleryItemActivity.this,"删除成功");
                        finish();
                    }else {
                        ToastUtils.showToast(GalleryItemActivity.this,model.error.getMsg());
                    }
                }
            }, new Consumer<Throwable>() {
                @Override public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            });
        getCompositeDisposable().add(disposable);
    }
    @Override public void showData(GalleryItemBean model) {
        if (model != null && model.getRows() != null && model.getRows().size() > 0) {
            if (page == 1) {
                adapter.setNewData(model.getRows());
            } else {
                adapter.addData(model.getRows());
            }
        } else {
            adapter.setNewData(new ArrayList<GalleryItem>());
        }
    }
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
