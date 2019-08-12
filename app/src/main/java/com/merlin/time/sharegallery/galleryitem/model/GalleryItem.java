package com.merlin.time.sharegallery.galleryitem.model;

import java.io.Serializable;

/**
 * @author zhouyang
 * @date 2018/12/30
 * @desc
 */
public class GalleryItem implements Serializable {
    private String id;
    private String parent_id;
    private String album_img_id;
    private String user_id;
    private String comment;
    private String is_deleted;
    private String c_t;
    private String u_t;
    private String head_img;

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

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }
}
