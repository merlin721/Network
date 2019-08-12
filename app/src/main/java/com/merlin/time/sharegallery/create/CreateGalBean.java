package com.merlin.time.sharegallery.create;

import java.io.Serializable;

/**
 * @author zhouyang
 * @date 2019/1/5
 * @desc id":539,"name":"\u5230\u5e951","user_id":293,"cover_img":"album_photo\/photo_img_empty.jpg","user_num":1,"img_num":0,"album_use_space":0,"status":1,"invitation_code":"","c_t":1546698841,"u_t":1546698841
 */
public class CreateGalBean implements Serializable {
    private String id;
    private String name;
    private String user_id;
    private String cover_img;
    private String album_photo;
    private String user_num;
    private String img_num;
    private String album_use_space;
    private String status;
    private String invitation_code;
    private String c_t;
    private String u_t;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public String getAlbum_photo() {
        return album_photo;
    }

    public void setAlbum_photo(String album_photo) {
        this.album_photo = album_photo;
    }

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public String getImg_num() {
        return img_num;
    }

    public void setImg_num(String img_num) {
        this.img_num = img_num;
    }

    public String getAlbum_use_space() {
        return album_use_space;
    }

    public void setAlbum_use_space(String album_use_space) {
        this.album_use_space = album_use_space;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
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
}
