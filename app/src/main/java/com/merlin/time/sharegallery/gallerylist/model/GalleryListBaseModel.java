package com.merlin.time.sharegallery.gallerylist.model;

import com.merlin.time.entity.BaseMode;
import java.io.Serializable;

/**
 * @author zhouyang
 * @date 2018/12/22
 * @desc
 */
public class GalleryListBaseModel extends BaseMode implements Serializable {
    private GalleryListModel data;

    public GalleryListModel getData() {
        return data;
    }

    public void setData(GalleryListModel data) {
        this.data = data;
    }
}
