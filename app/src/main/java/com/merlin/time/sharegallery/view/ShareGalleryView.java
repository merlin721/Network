package com.merlin.time.sharegallery.view;

import com.merlin.time.sharegallery.model.ShareGalleryBaseModel;
import com.soyoung.component_base.mvp.view.BaseMvpView;

/**
 * @author zhouyang
 * @date 2018/11/18
 * @desc
 */
public interface ShareGalleryView extends BaseMvpView {
    /**
     *
     * @param model
     */
    void showData(ShareGalleryBaseModel.ShareGalleryInnerModel model);
}
