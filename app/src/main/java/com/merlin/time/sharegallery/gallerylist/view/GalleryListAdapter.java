package com.merlin.time.sharegallery.gallerylist.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.merlin.time.R;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListItemModel;
import com.merlin.time.utils.SystemUtils;
import com.soyoung.component_base.image.ImageWorker;
import com.soyoung.component_base.util.LogUtils;
import com.soyoung.component_base.util.ScreenUtils;

/**
 * @author zhouyang
 * @date 2018/12/22
 * @desc 相册列表的适配器
 */
public class GalleryListAdapter extends BaseQuickAdapter<GalleryListItemModel,BaseViewHolder> {
    private Context context;

    public GalleryListAdapter(Context context) {
        super(R.layout.gallery_list_item);
        this.context = context;
    }

    @Override protected void convert(BaseViewHolder baseViewHolder, GalleryListItemModel model) {
        ImageView imageView = baseViewHolder.getView(R.id.gallery_list_item_img);
        ImageView video = baseViewHolder.getView(R.id.gallery_list_item_video);
        //JzvdStd playerStandard = baseViewHolder.getView(R.id.videoplayer);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,  RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.width = ScreenUtils.getScreenWidth()/4 -SystemUtils.dip2px(context,4);
        layoutParams.height = ScreenUtils.getScreenWidth()/4 -SystemUtils.dip2px(context,4);
        ImageWorker.loadImage(context,model.getImg_url_small(),imageView);
        imageView.setLayoutParams(layoutParams);
        if ("1".equals(model.getType())){
        //    imageView.setVisibility(View.VISIBLE);
            video.setVisibility(View.GONE);


        }else if ("2".equals(model.getType())){
        //    imageView.setVisibility(View.GONE);
            video.setVisibility(View.VISIBLE);
        //    String url =model.getVideo_url();
        //    if (url.contains("https")){
        //        url = url.replace("https","http");
        //    }
        //    LogUtils.e(url);
        //    //playerStandard.setUp(url, "时间视频", Jzvd.SCREEN_WINDOW_LIST);
        //    ImageWorker.loadImage(mContext,model.getImg_url_big(),playerStandard.thumbImageView);
        //    playerStandard.setLayoutParams(layoutParams);
        }
    }
}
