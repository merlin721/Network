package com.merlin.time.my.feedback.model;

import com.merlin.time.entity.BaseMode;

/**
 * @author zhouyang
 * @date 2018/11/25
 * @desc
 */
public class FeedbackBaseModel extends BaseMode {

    private FeedbackModel data;

    public FeedbackModel getData() {
        return data;
    }

    public void setData(FeedbackModel data) {
        this.data = data;
    }
}
