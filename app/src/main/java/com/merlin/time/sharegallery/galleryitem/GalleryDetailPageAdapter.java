package com.merlin.time.sharegallery.galleryitem;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.merlin.time.R;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListItemModel;
import com.soyoung.component_base.image.ImageWorker;
import com.soyoung.component_base.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * @author zhouyang
 * @date 2018/12/30
 * @desc
 */
public class GalleryDetailPageAdapter extends PagerAdapter {

    private List<GalleryListItemModel> contentData;
    private Context mContext;

    public GalleryDetailPageAdapter(Context context) {
        this.mContext = context;
        contentData = new ArrayList<>();
    }

    public void setContentData( List<GalleryListItemModel> data) {
        if (contentData.size()>0){
            contentData.clear();
        }
        contentData.addAll(data);
        notifyDataSetChanged();
    }

    @Override public int getCount() {
        return contentData == null ? 0 : contentData.size();
    }

    @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull @Override public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View rootView =
            LayoutInflater.from(mContext).inflate(R.layout.gallery_deital_header_item, null);
        ImageView imageView = rootView.findViewById(R.id.gallery_detail_header_photo_view);
        JzvdStd playerStandard = rootView.findViewById(R.id.videoplayer);
        if (contentData.size() > 0) {
            if ("1".equals(contentData.get(position).getType())){
                imageView.setVisibility(View.VISIBLE);
                playerStandard.setVisibility(View.GONE);
                ImageWorker.loadImage(mContext, contentData.get(position).getImg_url_big(), imageView);
            }else if ("2".equals(contentData.get(position).getType())){
                imageView.setVisibility(View.GONE);
                playerStandard.setVisibility(View.VISIBLE);
                String url =contentData.get(position).getVideo_url();
                if (url.contains("https")){
                    url = url.replace("https","http");
                }
                LogUtils.e(url);
                playerStandard.setUp(url, "时间视频",Jzvd.SCREEN_WINDOW_NORMAL);
                ImageWorker.loadImage(mContext,contentData.get(position).getImg_url_big(),playerStandard.thumbImageView);
                //playerStandard.startButton.performClick();
            }
        }
        container.addView(rootView);
        return rootView;
    }
}
