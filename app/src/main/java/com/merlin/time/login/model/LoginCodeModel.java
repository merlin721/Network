package com.merlin.time.login.model;

import java.io.Serializable;

/**
 * @author zhouyang
 * @date 2018/11/11
 * @desc
 * id: 293,
 * name: "merlin",
 * phone: "15600900176",
 * head_img: "https://test.taowangzhan.com/app_upload/head_img/user_head_img_293/175_175/293-5c4d96dc8398b.jpg",
 * init_space: 10485760,
 * total_space: 10485760,
 * use_space: 103166,
 * openid: "oD01K5D94XqMWNIf5FrUhWGEdkD8",
 * invitation_user_id: 0,    签到邀请的id
 * last_view_album_img_id: 1551708618,    ？
 * is_sign_in: 0, 是否签到
 * used_rate: 0, ？
 * used_total_desc: "0.1G/10G",   这个是否就是进度条展示的文字 ？
 * space_is_notice: 0 ？
 * id 用户id
 * name 用户名
 * phone 用户手机号
 * head_img 用户头像
 * init_space 初始空间，暂时用不到
 * total_space 用户总空间大小 单位K
 * use_space 用户使用的空间大小 单位k
 * openid
 * invitation_user_id  邀请此用户的人的用户id
 * last_view_album_img_id 暂时用不到
 * is_sign_in  今天是否签到 1:今天已签到  0：今天还未签到
 * used_rate  用户使用的空间百分比
 * used_total_desc 用户使用的空间百分比描述
 * space_is_notice 是否提醒用户空间是否超过阀值  1：提醒  0：不提醒
 */
public class LoginCodeModel implements Serializable {
  private String id;
  private String name;
  private String passwd;
  private String phone;
  private String head_img;
  private String init_space;
  private String total_space;
  private String use_space;
  private String invitation_user_id;
  private String last_view_album_img_id;
  private String used_total_desc;
  private String used_rate;
  //是否提醒用户空间是否超过阀值  1：提醒  0：不提醒
  private String space_is_notice;
  private String is_sign_in;
  private String c_t;
  private String u_t;
  private String openid;
  private String token;

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

  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getHead_img() {
    return head_img;
  }

  public void setHead_img(String head_img) {
    this.head_img = head_img;
  }

  public String getTotal_space() {
    return total_space;
  }

  public void setTotal_space(String total_space) {
    this.total_space = total_space;
  }

  public String getUse_space() {
    return use_space;
  }

  public void setUse_space(String use_space) {
    this.use_space = use_space;
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

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getInit_space() {
    return init_space;
  }

  public void setInit_space(String init_space) {
    this.init_space = init_space;
  }

  public String getInvitation_user_id() {
    return invitation_user_id;
  }

  public void setInvitation_user_id(String invitation_user_id) {
    this.invitation_user_id = invitation_user_id;
  }

  public String getLast_view_album_img_id() {
    return last_view_album_img_id;
  }

  public void setLast_view_album_img_id(String last_view_album_img_id) {
    this.last_view_album_img_id = last_view_album_img_id;
  }

  public String getUsed_total_desc() {
    return used_total_desc;
  }

  public void setUsed_total_desc(String used_total_desc) {
    this.used_total_desc = used_total_desc;
  }

  public String getUsed_rate() {
    return used_rate;
  }

  public void setUsed_rate(String used_rate) {
    this.used_rate = used_rate;
  }

  public String getSpace_is_notice() {
    return space_is_notice;
  }

  public void setSpace_is_notice(String space_is_notice) {
    this.space_is_notice = space_is_notice;
  }

  public String getIs_sign_in() {
    return is_sign_in;
  }

  public void setIs_sign_in(String is_sign_in) {
    this.is_sign_in = is_sign_in;
  }
}
