package com.merlin.time.my.feedback.model;

import com.merlin.time.entity.BaseMode;
import com.merlin.time.sharegallery.galleryitem.model.GalleryItemBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author merlin720
 * @date 2019/2/16
 * @mail zy44638@gmail.com
 * @description
 */
public class FeedbackListBaseBean extends BaseMode implements Serializable {
    private List<FeedbackListBean> data;

    public List<FeedbackListBean> getData() {
        return data;
    }

    public void setData(List<FeedbackListBean> data) {
        this.data = data;
    }
}
