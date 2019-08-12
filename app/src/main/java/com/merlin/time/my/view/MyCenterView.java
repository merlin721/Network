package com.merlin.time.my.view;

import com.merlin.time.my.model.MyCenterModel;
import com.merlin.time.my.model.MyCenterSignBean;
import com.merlin.time.sharegallery.model.ShareGalleryBaseModel;
import com.soyoung.component_base.mvp.view.BaseMvpView;

/**
 * @author zhouyang
 * @date 2018/11/18
 * @desc
 */
public interface MyCenterView extends BaseMvpView {
    /**
     *
     * @param model
     */
    void showData(MyCenterModel model);
    void signSuccess(MyCenterSignBean model);
    void signedSuccess();
}
