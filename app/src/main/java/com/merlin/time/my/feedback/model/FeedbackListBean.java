package com.merlin.time.my.feedback.model;

import java.io.Serializable;

/**
 * @author merlin720
 * @date 2019/2/16
 * @mail zy44638@gmail.com
 * @description
 */
public class FeedbackListBean implements Serializable {
    private String id;
    private String user_id;
    private String content;
    private String c_t;
    private String u_t;
    //1为用户写的内容；2为客服回复的内容；
    private String type;

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

    public String getC_t() {
        return c_t;
    }

    public void setC_t(String c_t) {
        this.c_t = c_t;
    }

    public String getU_t() {
        return u_t;
    }

    public void setU_t(String u_t) {
        this.u_t = u_t;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
