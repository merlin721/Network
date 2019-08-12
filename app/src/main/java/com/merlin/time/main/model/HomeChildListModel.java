package com.merlin.time.main.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author merlin720
 * @date 2019/2/23
 * @mail zy44638@gmail.com
 * @description "id": 8520,
 *             "img_url": 原图的地址，这个列表用的是下面的中图,
 *             "video_url": 视频地址，通过下面的type来判断是视频还是图片,
 *             "img_md5": "fab28cc942ae0b5694c8919698fcd1f9",
 *             "user_id": 用户ID
 *             "album_id": 383,
 *             "status": 照片状态，0为删除待服务器脚本删除，1为正常状态；
 *             "c_t": 1544861605,
 *             "u_t": 修改时间
 *             "comment_num": 评论数量
 *             "size": 照片大小，单位M
 *             "img_address": 照片详细地址
 *             "img_area": 照片地区
 *             "img_city": 照片城市
 *             "lng": "116.39524722222",
 *             "lat": "39.971133333333",
 *             "exif_time": 拍摄时间戳
 *             "type": 1为照片，2为视频
 *             "width": 照片宽度
 *             "height": 照片长度
 *             "poi_type": "",
 *             "user_like_name": 是否有人点赞
 *             "img_url_small":
 *             "img_url_big": 960的中图
 *             "img_time": "2018-12-15 15:57",
 *             "img_time_alias":
 *             "img_user_name":上传者名称
 *             "head_img":上传者头像
 *             "comment_list": [],
 *             "is_like": 0//上传者是否点赞
 */
public class HomeChildListModel implements Serializable , MultiItemEntity {
    /**
     * 区分条目类型的
     */
    private int itemType = 1;

    private String id;
    private String img_url;
    private String video_url;
    private String img_md5;
    private String user_id;
    private String album_id;
    private String status;
    private String c_t;
    private String u_t;
    private String comment_num;
    private String size;
    private String img_address;
    private String img_area;
    private String img_city;
    private String lng;
    private String lat;
    private String exif_time;
    private String type;
    private String width;
    private String height;
    private String poi_type;
    private List<String> user_like_name;
    private String img_url_small;
    private String img_url_big;
    private String img_time;
    private String img_time_alias;
    private String img_user_name;
    private String head_img;
    private List<CommonListModel> comment_list;
    private String is_like;
    /**
     * 上传时间
     */
    private String time_desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImg_area() {
        return img_area;
    }

    public void setImg_area(String img_area) {
        this.img_area = img_area;
    }

    public String getImg_city() {
        return img_city;
    }

    public void setImg_city(String img_city) {
        this.img_city = img_city;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getExif_time() {
        return exif_time;
    }

    public void setExif_time(String exif_time) {
        this.exif_time = exif_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPoi_type() {
        return poi_type;
    }

    public void setPoi_type(String poi_type) {
        this.poi_type = poi_type;
    }

    public List<String> getUser_like_name() {
        return user_like_name;
    }

    public void setUser_like_name(List<String> user_like_name) {
        this.user_like_name = user_like_name;
    }

    public String getImg_url_small() {
        return img_url_small;
    }

    public void setImg_url_small(String img_url_small) {
        this.img_url_small = img_url_small;
    }

    public String getImg_url_big() {
        return img_url_big;
    }

    public void setImg_url_big(String img_url_big) {
        this.img_url_big = img_url_big;
    }

    public String getImg_time() {
        return img_time;
    }

    public void setImg_time(String img_time) {
        this.img_time = img_time;
    }

    public String getImg_time_alias() {
        return img_time_alias;
    }

    public void setImg_time_alias(String img_time_alias) {
        this.img_time_alias = img_time_alias;
    }

    public String getImg_user_name() {
        return img_user_name;
    }

    public void setImg_user_name(String img_user_name) {
        this.img_user_name = img_user_name;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public List<CommonListModel> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<CommonListModel> comment_list) {
        this.comment_list = comment_list;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getTime_desc() {
        return time_desc;
    }

}
