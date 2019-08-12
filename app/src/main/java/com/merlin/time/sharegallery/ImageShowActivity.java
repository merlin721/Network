package com.merlin.time.sharegallery;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.chrisbanes.photoview.PhotoView;
import com.merlin.time.R;
import com.soyoung.component_base.image.ImageWorker;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.LogUtils;

/**
 * @author merlin
 * @date 2018年11月11日
 * desc 首页
 */
public class ImageShowActivity extends BaseActivity {

    PhotoView photoView;

    private String imageUrl;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_image_show;
    }

    @Override
    protected void initView() {
        photoView = findViewById(R.id.image_show_photo_view);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        imageUrl = getIntent().getStringExtra("image_url");
        LogUtils.e(imageUrl);
        ImageWorker.loadImage(ImageShowActivity.this,imageUrl,photoView );

    }

    @Override
    protected void setListener() {

    }


}
