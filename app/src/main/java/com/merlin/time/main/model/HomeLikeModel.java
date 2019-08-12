package com.merlin.time.main.model;

import com.merlin.time.entity.BaseMode;

import java.io.Serializable;

/**
 * @author merlin720
 * @date 2019/3/3
 * @mail zy44638@gmail.com
 * @description
 * user_id": 1,
 * "album_img_id": "4",
 * "u_t": 1549864580,
 * "c_t": 1549864580,
 * "id": 3
 */
public class HomeLikeModel implements Serializable {
    private String user_id;
    private String u_t;
    private String c_t;
    private String album_img_id;
    private String id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getU_t() {
        return u_t;
    }

    public void setU_t(String u_t) {
        this.u_t = u_t;
    }

    public String getC_t() {
        return c_t;
    }

    public void setC_t(String c_t) {
        this.c_t = c_t;
    }

    public String getAlbum_img_id() {
        return album_img_id;
    }

    public void setAlbum_img_id(String album_img_id) {
        this.album_img_id = album_img_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
