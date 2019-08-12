package com.merlin.time.main.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * @author merlin720
 * @date 2019/3/2
 * @mail zy44638@gmail.com
 * @description
 * "id": 518,
 * 	"parent_id": 0,
 * 	"album_img_id": 28215,
 * 	"user_id": 293,
 * 	"comment": "电话打的",
 * 	"is_deleted": 0,
 * 	"c_t": 1550913084,
 * 	"u_t": 1550913084,
 * 	"user_name": "merlin",
 * 	"user_head_img": "https:\/\/test.taowangzhan.com\/app_upload\/head_img\/user_head_img_293\/175_175\/293-5c4d96dc8398b.jpg",
 * 	"time_desc": "6天前"
 */
public class CommonListModel implements Serializable , MultiItemEntity {
    private String id;
    private String parent_id;
    private String album_img_id;
    private String user_id;
    private String comment;
    private String is_deleted;
    private String c_t;
    private String u_t;
    private String user_name;
    private String user_head_img;
    private String time_desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getAlbum_img_id() {
        return album_img_id;
    }

    public void setAlbum_img_id(String album_img_id) {
        this.album_img_id = album_img_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_head_img() {
        return user_head_img;
    }

    public void setUser_head_img(String user_head_img) {
        this.user_head_img = user_head_img;
    }

    public String getTime_desc() {
        return time_desc;
    }

    public void setTime_desc(String time_desc) {
        this.time_desc = time_desc;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
