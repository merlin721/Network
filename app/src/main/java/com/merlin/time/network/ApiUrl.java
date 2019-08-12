package com.merlin.time.network;

import com.soyoung.component_base.network.AppBaseUrlConfig;

/**
 * @author zhouyang
 * @date 2018/10/28
 * @desc
 */
public interface ApiUrl {
    //http://time.taowangzhan.com/api/user/send-code?phone=17600902763
    /** 发验证码 */
    String sendCode = AppBaseUrlConfig.getBaseUrl() + "/api/user/send-code";
    /**
     * 登录
     * http://time.taowangzhan.com/api/user/check-code?phone=17600902763&code=2264&openid=ohuQP5aepVI9Yijkuz_zEVenjaXU
     */
    String checkCode = AppBaseUrlConfig.getBaseUrl() + "/api/user/check-code";

    /**
     * 3.检测登录
     *
     * http://time.taowangzhan.com/api/user/access?token=v_2c0e928994c247e342c7e745d39ede10_1_1517658114
     */

    String checkAccess = AppBaseUrlConfig.getBaseUrl() +"/api/user/access";
    /**
     * 获取我的相册列表
     * http://time.taowangzhan.com/api/album-user/get-my-album-list?token=v_19f75ec5361e7d263fdc6072ee09cbfd_1_1539066677&page=1&size=10
     */
    String getMyAlbumList = AppBaseUrlConfig.getBaseUrl() + "/api/album-user/get-my-album-list";

    String sendFeedback = AppBaseUrlConfig.getBaseUrl() + "/api/feedback/create";
    //http://time.taowangzhan.com/api/user/modify-user-info?token=v_ee0cf3b68b1ea084e4a344e529ba5722_1_1517035627&name=123&head_img=1111

    String editName = AppBaseUrlConfig.getBaseUrl() + "/api/user/modify-user-info";

    //http://time.taowangzhan.com/api/album/create?token=v_2c0e928994c247e342c7e745d39ede10_1_1517658114&name=2019年底
    String createGallery = AppBaseUrlConfig.getBaseUrl() + "/api/album/create";
    //http://time.taowangzhan.com/api/user/modify-user-head-img?token=v_2c0e928994c247e342c7e745d39ede10_1_1517658114
    String uploadHead = AppBaseUrlConfig.getBaseUrl() + "/api/user/modify-user-head-img";
    /**
     * 获取相册图片列表
     */
    //http://time.taowangzhan.com/api/album-img/get-album-img-list?album_id=1&token=v_ee0cf3b68b1ea084e4a344e529ba5722_1_1517035627&page=2&size=2
    String getAlbumImgList = AppBaseUrlConfig.getBaseUrl() + "/api/album-img/get-album-img-list";

    //http://time.taowangzhan.com/api/ali-img-upload/get-policy-and-callback
    String getPolicyCallback = AppBaseUrlConfig.getBaseUrl() + "/api/ali-img-upload/get-policy-and-callback";

    //http://time.taowangzhan.com/api/comment/get-comment-list?token=v_2c0e928994c247e342c7e745d39ede10_1_1517658114&album_img_id=1&page=2&size=2
    String getCommentList = AppBaseUrlConfig.getBaseUrl() + "/api/comment/get-comment-list";

    //http://time.taowangzhan.com/api/album-img/upload?token=v_ee0cf3b68b1ea084e4a344e529ba5722_1_1517035627&album_id=1
    String uploadImg = AppBaseUrlConfig.getBaseUrl() + "/api/album-img/upload";

    //http://time.taowangzhan.com/api/album-img/delete?album_img_id=16&token=v_2c0e928994c247e342c7e745d39ede10_1_1517658114
    String deletePic = AppBaseUrlConfig.getBaseUrl() + "/api/album-img/delete";

    String getShareInfo = AppBaseUrlConfig.getBaseUrl() + "/api/album/get-album-info";

    //http://dev.time.taowangzhan.com/api/feedback/get-list?token=Token&from=From
    String feedbackList = AppBaseUrlConfig.getBaseUrl() + "/api/feedback/get-list";
    //获取首页喜欢列表的接口
    //http://dev.time.taowangzhan.com/api/album-img/get-tab-data?token=v_19f75ec5361e7d263fdc6072ee09cbfd_1_1539066677&tab=
    //http://dev.time.taowangzhan.com/api/album-img/get-like-img-list?token=v_19f75ec5361e7d263fdc6072ee09cbfd_1_1539066677&page=1&size=10&from=1
    String getTabDta = AppBaseUrlConfig.getBaseUrl() + "/api/album-img/get-tab-data";


    //http://time.taowangzhan.com/api/user-like/like?token=Token&album_img_id=Album_img_id&from=From
    String like = AppBaseUrlConfig.getBaseUrl() + "/api/user-like/like";
    //http://time.taowangzhan.com/api/comment/create?token=v_2c0e928994c247e342c7e745d39ede10_1_1517658114&album_img_id=1&comment=1111
    String discuss = AppBaseUrlConfig.getBaseUrl() + "/api/comment/create";
    //签到
    //http://time.taowangzhan.com/api/user-sign-in/sign-in?token=TOKEN
    String sign = AppBaseUrlConfig.getBaseUrl() + "/api/user-sign-in/sign-in";
    //http://time.taowangzhan.com/api/user-space/get-space-log?token=Token
    String spaceLog = AppBaseUrlConfig.getBaseUrl() + "/api/user-space/get-space-log";
}
