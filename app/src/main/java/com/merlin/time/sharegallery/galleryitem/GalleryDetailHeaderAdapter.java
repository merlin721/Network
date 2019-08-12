package com.merlin.time.sharegallery.galleryitem;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.merlin.time.R;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListItemModel;
import com.soyoung.component_base.image.ImageWorker;

/**
 * @author zhouyang
 * @date 2018/12/23
 * @desc
 */
public class GalleryDetailHeaderAdapter extends BaseQuickAdapter<GalleryListItemModel,BaseViewHolder> {

    private Context context;

    public GalleryDetailHeaderAdapter(Context context) {
        super(R.layout.gallery_deital_header_item);
        this.context = context;
    }

    @Override protected void convert(BaseViewHolder baseViewHolder,
        GalleryListItemModel galleryListItemModel) {
        ImageWorker.loadImage(context,galleryListItemModel.getImg_url_big(),baseViewHolder.getView(R.id.gallery_detail_header_photo_view));

    }
}
