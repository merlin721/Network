package com.merlin.time.sharegallery.galleryitem.model;

import com.merlin.time.entity.BaseMode;
import java.io.Serializable;

/**
 * @author zhouyang
 * @date 2018/12/30
 * @desc
 */
public class GalleryItemBaseBean extends BaseMode implements Serializable {
    private GalleryItemBean data;

    public GalleryItemBean getData() {
        return data;
    }

    public void setData(GalleryItemBean data) {
        this.data = data;
    }
}
