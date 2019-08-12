package com.merlin.time.main.model;

import com.merlin.time.entity.BaseMode;

/**
 * @author merlin720
 * @date 2019/3/3
 * @mail zy44638@gmail.com
 * @description
 */
public class HomeCommonBaseModel extends BaseMode {
    private HomeCommonModel data;

    public HomeCommonModel getData() {
        return data;
    }

    public void setData(HomeCommonModel data) {
        this.data = data;
    }
}
