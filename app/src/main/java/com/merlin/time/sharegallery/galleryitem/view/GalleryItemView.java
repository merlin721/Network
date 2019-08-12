package com.merlin.time.sharegallery.galleryitem.view;

import com.merlin.time.sharegallery.galleryitem.model.GalleryItemBaseBean;
import com.merlin.time.sharegallery.galleryitem.model.GalleryItemBean;
import com.merlin.time.sharegallery.gallerylist.model.GalleryListModel;
import com.soyoung.component_base.mvp.view.BaseMvpView;

/**
 * @author zhouyang
 * @date 2018/12/22
 * @desc
 */
public interface GalleryItemView extends BaseMvpView {
    /**
     * 展示数据
     * @param model
     */
    void showData(GalleryItemBean model);
}
