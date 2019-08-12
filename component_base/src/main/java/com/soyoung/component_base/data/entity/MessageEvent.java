package com.soyoung.component_base.data.entity;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/4/17
 * description   : 事件消息
 */

public class MessageEvent<T>{
    private String msgTag;
    private T object;

    public MessageEvent(String msgTag, T object) {
        this.msgTag = msgTag;
        this.object = object;
    }

    public MessageEvent(String msgTag) {
        this.msgTag = msgTag;
    }

    public String getMsgTag() {
        return msgTag;
    }

    public void setMsgTag(String msgTag) {
        this.msgTag = msgTag;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
