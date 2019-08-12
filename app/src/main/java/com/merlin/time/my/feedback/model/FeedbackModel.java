package com.merlin.time.my.feedback.model;

import java.io.Serializable;

/**
 * @author zhouyang
 * @date 2018/11/25
 * @desc
 */
public class FeedbackModel implements Serializable {
    private String id;
    private String user_id;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
