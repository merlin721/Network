package com.merlin.time.my.space;

import java.io.Serializable;

/**
 * @author merlin720
 * @date 2019/3/30
 * @mail zy44638@gmail.com
 * @description
 *
 *   "id": 2,
 *         "user_id": 1,
 *         "type": 1是签到送空间，2是邀请好友送的空间
 *         "space": 空间大小，单位是KB
 *         "c_t": 1549940830,
 *         "u_t": 1549940830,
 *         "desc": 赠送空间的名称描述
 */
public class SpaceDetailBean implements Serializable {
  private String id;
  private String user_id;
  private String type;
  private String space;
  private String desc;
  private String c_t;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSpace() {
    return space;
  }

  public void setSpace(String space) {
    this.space = space;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getC_t() {
    return c_t;
  }

  public void setC_t(String c_t) {
    this.c_t = c_t;
  }
}
