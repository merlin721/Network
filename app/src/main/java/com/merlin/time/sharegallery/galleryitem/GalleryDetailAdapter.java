package com.merlin.time.sharegallery.galleryitem;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.merlin.time.R;
import com.merlin.time.sharegallery.galleryitem.model.GalleryItem;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListItemModel;
import com.soyoung.component_base.image.ImageWorker;

/**
 * @author zhouyang
 * @date 2018/12/23
 * @desc
 */
public class GalleryDetailAdapter extends BaseQuickAdapter<GalleryItem,BaseViewHolder> {

    public GalleryDetailAdapter() {
        super(R.layout.gallery_item_item);
    }

    @Override protected void convert(BaseViewHolder baseViewHolder,
        GalleryItem galleryListItemModel) {
        ImageWorker.loadImageCircle(mContext,galleryListItemModel.getHead_img(),baseViewHolder.getView(R.id.gal_item_list_item));

        baseViewHolder.setText(R.id.gal_item_list_item_name,galleryListItemModel.getComment());
        baseViewHolder.setText(R.id.gal_item_list_item_content,galleryListItemModel.getComment());
        baseViewHolder.setText(R.id.gal_item_list_item_time,galleryListItemModel.getComment());
    }
}
