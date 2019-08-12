package com.merlin.time.main;

import com.merlin.time.main.model.HomeChildModel;
import com.merlin.time.main.model.HomeCommonModel;
import com.merlin.time.main.model.HomeLikeModel;
import com.merlin.time.sharegallery.model.ShareGalleryBaseModel;
import com.soyoung.component_base.mvp.view.BaseMvpView;

/**
 * @author merlin720
 * @date 2019/2/23
 * @mail zy44638@gmail.com
 * @description
 */
public interface HomeChildView extends BaseMvpView {
    /**
     *
     * @param model
     */
    void showData(HomeChildModel model);

    /**
     * 点赞成功
     * @param model
     */
    void doLike(HomeLikeModel model,int position);

    void doCommon(HomeCommonModel model,int position);
}
