package com.merlin.time.my.edit.model;

import java.io.Serializable;

/**
 * @author zhouyang
 * @date 2018/12/2
 * @desc {"id":32827,"img_url":"album_photo\/Screenshot_2019-01-17-18-28-43-782_\u9ad8\u5fb7\u5730\u56fe.png","video_url":"album_photo\/Screenshot_2019-01-17-18-28-43-782_\u9ad8\u5fb7\u5730\u56fe.png","img_md5":"","user_id":293,"album_id":"385","status":1,"c_t":1551522218,"u_t":1551522218,"comment_num":0,"size":654,"img_address":"","img_area":"","img_city":"","lng":0,"lat":0,"exif_time":0,"type":1,"width":"1080","height":"2060","poi_type":"","user_like_name":null}
 */
public class UploadPicBean implements Serializable {
    private String img_url;
    private String video_url;
    private String id;
    private String img_md5;
    private String user_id;
    private String album_id;
    private String status;
    private String comment_num;
    private String size;
    private String img_address;
    private String width;
    private String height;
    private String user_like_name;
    private String is_upload;
    private String head_img;

    public String getIs_upload() {
        return is_upload;
    }

    public void setIs_upload(String is_upload) {
        this.is_upload = is_upload;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_md5() {
        return img_md5;
    }

    public void setImg_md5(String img_md5) {
        this.img_md5 = img_md5;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImg_address() {
        return img_address;
    }

    public void setImg_address(String img_address) {
        this.img_address = img_address;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getUser_like_name() {
        return user_like_name;
    }

    public void setUser_like_name(String user_like_name) {
        this.user_like_name = user_like_name;
    }
}
