package com.merlin.time.sharegallery.view;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.merlin.time.R;
import com.merlin.time.sharegallery.model.ShareGalleryModel;
import com.soyoung.component_base.image.ImageWorker;

/**
 * @author zhouyang
 * @date 2018/11/18
 * @desc
 */
public class ShareGalleryAdapter extends BaseQuickAdapter<ShareGalleryModel,BaseViewHolder> {
    public ShareGalleryAdapter() {
        super(R.layout.share_gallery_common_item);
    }

    @Override protected void convert(BaseViewHolder baseViewHolder, ShareGalleryModel s) {
        ImageWorker.loadImage(mContext,s.getCover_img(),baseViewHolder.getView(R.id.share_gal_item_img));
        baseViewHolder.setText(R.id.share_gal_item_title,s.getName());
        baseViewHolder.setText(R.id.share_gal_item_desc,s.getStatus().equals("1")? "照片" +s.getImg_num()+" | 成员"+s.getUser_num(): "视频"+"|成员"+s.getUser_num());
    }
}
