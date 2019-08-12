package com.merlin.time.my.model;

import java.io.Serializable;

/**
 * @author merlin720
 * @date 2019/3/24
 * @mail zy44638@gmail.com
 * @description "user_id": 1,
 * "date": 1549900800,
 * "u_t": 1549940565,
 * "c_t": 1549940565,
 * "id": 4,
 * "add_space_size": 空间大小，单位是KB；
 * {"ret":1,"data":{"user_id":293,"date":1553356800,"u_t":1553430955,"c_t":1553430955,"id":26,"add_space_size":30,"space_give_multiple":3}}
 */
public class MyCenterSignBean implements Serializable {
  private String user_id;
  private String date;
  private String id;
  private String add_space_size;
  private String u_t;
  private String c_t;

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAdd_space_size() {
    return add_space_size;
  }

  public void setAdd_space_size(String add_space_size) {
    this.add_space_size = add_space_size;
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
}
