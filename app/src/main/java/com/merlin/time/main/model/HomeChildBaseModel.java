package com.merlin.time.main.model;

import com.merlin.time.entity.BaseMode;

import java.io.Serializable;

/**
 * @author merlin720
 * @date 2019/2/23
 * @mail zy44638@gmail.com
 * @description
 */
public class HomeChildBaseModel extends BaseMode implements Serializable {
    private HomeChildModel data;

    public HomeChildModel getData() {
        return data;
    }

    public void setData(HomeChildModel data) {
        this.data = data;
    }
}
