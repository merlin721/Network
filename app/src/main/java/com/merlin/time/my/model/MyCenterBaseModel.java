package com.merlin.time.my.model;

import com.merlin.time.entity.BaseMode;
import com.merlin.time.sharegallery.model.ShareGalleryBaseModel;

/**
 * @author zhouyang
 * @date 2018/11/25
 * @desc
 */
public class MyCenterBaseModel extends BaseMode {
    private MyCenterModel data;

    public MyCenterModel getData() {
        return data;
    }

    public void setData(MyCenterModel data) {
        this.data = data;
    }
}
