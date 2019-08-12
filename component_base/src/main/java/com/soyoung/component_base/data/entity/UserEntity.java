package com.soyoung.component_base.data.entity;

import java.io.Serializable;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/9/27
 * description   : 用户实体类
 */

public class UserEntity implements Serializable {
    /**
     * access_token : sdsdsddcxcxcxccxcxcxcxcxcx
     * avtar : http://example.com/sss.jpg
     * doc_id : 2222
     * is_answerer : 1
     * name : 医生用户名
     * uid : 1111
     */
    private static final long serialVersionUID = -1231154287L;

    private String access_token;
    private String avtar;
    private String doc_id;
    private String is_answerer;
    private String name;
    private String uid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAvtar() {
        return avtar;
    }

    public void setAvtar(String avtar) {
        this.avtar = avtar;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getIs_answerer() {
        return is_answerer;
    }

    public void setIs_answerer(String is_answerer) {
        this.is_answerer = is_answerer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
