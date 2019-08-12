package com.merlin.time.entity;

import java.io.Serializable;

/**
 * @author zhouyang
 * @date 2018/10/28
 * @desc
 */
public class ErrorMode implements Serializable {
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
