package com.merlin.time.sharegallery.model;

import com.merlin.time.entity.BaseMode;
import java.io.Serializable;

/**
 * @author zhouyang
 * @date 2018/11/18
 * @desc id: 13,
 * name: "欣冉的一家",
 * user_id: 15,
 * cover_img: "http://test.taowangzhan.com/app_upload/img/84bc7f93a/175_175/84bc7f93ad76ff7966795c1e217582a0.jpg",
 * user_num: 5,
 * img_num: 124,
 * status: 1,
 * invitation_code: "",
 * c_t: 1528726424,
 * u_t: 1538662183
 *
 * id: 1,
 * name: "家",
 * user_id: 1,
 * cover_img: "https://timebottle.oss-cn-beijing.aliyuncs.com/album_photo/tmp_761bba82b24bda3fe6637fc24a184701.jpg?x-oss-process=image/resize,m_fill,w_175,h_175",
 * user_num: 3,
 * img_num: 4,
 * album_use_space: 3202,
 * status: 1,
 * invitation_code: "AFC6248F3C8",
 * c_t: 1526695089,
 * u_t: 1553522519,
 * type: 2,
 * tab: 1,
 * auth: {
 * id: 1,
 * album_id: 1,
 * is_allow_share: 1,
 * is_allow_upload: 1,
 * is_allow_comment: 1,
 * c_t: 0,
 * u_t: 0
 * }
 * },
 */
public class ShareGalleryModel implements Serializable {
    private String id;
    private String name;
    private String user_id;
    private String cover_img;
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

    public String getAlbum_use_space() {
        return album_use_space;
    }

    public void setAlbum_use_space(String album_use_space) {
        this.album_use_space = album_use_space;
    }
}
