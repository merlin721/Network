package com.merlin.time.sharegallery.gallerylist.view;

import com.merlin.time.sharegallery.gallerylist.model.GalleryListCallBackModel;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListModel;
import com.soyoung.component_base.mvp.view.BaseMvpView;

/**
 * @author zhouyang
 * @date 2018/12/22
 * @desc
 */
public interface GalleryListView extends BaseMvpView {
    /**
     * 展示数据
     *
     * @param model
     */
    void showData(GalleryListModel model);

    /**
     * 获取上传阿里云的回调参数
     * @param model
     */
    void setCallbackParams(GalleryListCallBackModel model);
}
