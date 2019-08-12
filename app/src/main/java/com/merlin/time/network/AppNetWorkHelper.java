package com.merlin.time.network;

import com.androidnetworking.common.Priority;
import com.google.gson.JsonObject;
import com.soyoung.component_base.Constant;
import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.network.AppApiHelper;
import io.reactivex.Observable;
import java.io.File;
import java.util.HashMap;
import org.json.JSONObject;

/**
 * @author zhouyang
 * @date 2018/10/28
 * @desc
 */
public class AppNetWorkHelper extends AppApiHelper {
    private AppNetWorkHelper() {
    }

    private static class AppNetWorkHelperLoader {
        private static final AppNetWorkHelper INSTANCE = new AppNetWorkHelper();
    }

    public static AppNetWorkHelper getInstance() {
        return AppNetWorkHelperLoader.INSTANCE;
    }

    /**
     * 发送验证码
     */
    public Observable<JSONObject> sendCode(String phone) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);
        return post(ApiUrl.sendCode, params);
    }

    /**
     * 发送验证码
     *
     * @param phone 电话号码
     * @param code 验证码
     */
    public Observable<JSONObject> checkCode(String phone, String code) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);
        return post(ApiUrl.checkCode, params);
    }

    public Observable<JSONObject> checkAccess(String token) {
        HashMap<String, String> params = new HashMap<>();
        return post(ApiUrl.checkAccess, params);
    }

    public Observable<JSONObject>  getShareList(int page){
        HashMap<String,String> params = new HashMap<>();
        params.put("page",String.valueOf(page));
        params.put("size","10");
        params.put("type","2");
        return post(ApiUrl.getMyAlbumList,params);
    }

    public Observable<JSONObject> sendFeedback(String content){
        HashMap<String,String> params = new HashMap<>();
        params.put("content",content);
        return post(ApiUrl.sendFeedback,params);
    }

    public Observable<JSONObject> editName(String name){
        HashMap<String,String> params = new HashMap<>();
        params.put("name",name);
        return post(ApiUrl.editName,params);
    }

    public Observable<JSONObject>  getTabData(int page,String tab){
        HashMap<String,String> params = new HashMap<>();
        params.put("page",String.valueOf(page));
        params.put("size","10");
        params.put("tab",tab);
//        params.put("type","1,2");
        return post(ApiUrl.getTabDta,params);
    }


    /**
     * 创建相册
     * @param name
     * @return
     */
    public Observable<JSONObject> createGallery(String name){
        HashMap<String,String> params = new HashMap<>();
        params.put("name",name);
        return post(ApiUrl.createGallery,params);
    }

    /**
     * 相册列表
     * @param album_id
     * @param page
     * @param size
     * @return
     */
    public Observable<JSONObject> getGalleryList(String album_id,int page,int size){
        HashMap<String,String> params = new HashMap<>();
        params.put("album_id",album_id);
        params.put("page",String.valueOf(page));
        params.put("size",String.valueOf(size));
        return post(ApiUrl.getAlbumImgList,params);
    }

    public Observable<JSONObject> getUploadCallback(){
        HashMap<String,String> params = new HashMap<>();
        return post(ApiUrl.getPolicyCallback,params);
    }

    public Observable<JSONObject> getCommentList(String album_id,int page,int size){
        HashMap<String,String> params = new HashMap<>();
        params.put("album_img_id",album_id);
        params.put("page",String.valueOf(page));
        params.put("size",String.valueOf(size));
        return post(ApiUrl.getCommentList,params);
    }
    public Observable<JSONObject>  deletePic(String id){
        HashMap<String,String> params = new HashMap<>();
        params.put("album_img_id",String.valueOf(id));
        return post(ApiUrl.deletePic,params);
    }
    public Observable<JSONObject>  getShareInfo(){
        HashMap<String,String> params = new HashMap<>();
        return post(ApiUrl.getShareInfo,params);
    }

    /**
     *  获取反馈列表
     * @param from 1.iOS；2.安卓；3.小程序；4.网页版
     * @return
     */
    public Observable<JSONObject>  feedbackList(String from){
        HashMap<String,String> params = new HashMap<>();
        params.put("from",from);
        return post(ApiUrl.feedbackList,params);
    }


    public Observable<JSONObject> homeLike(String id){
        HashMap<String,String> params = new HashMap<>();
        params.put("album_img_id",id);
        return post(ApiUrl.like,params);
    }

    public Observable<JSONObject> homeDiscuss(String id,String content){
        HashMap<String,String> params = new HashMap<>();
        params.put("album_img_id",id);
        params.put("comment",content);
        return post(ApiUrl.discuss,params);
    }

    public Observable<JSONObject> sign(){
        HashMap<String,String> params = new HashMap<>();

        return post(ApiUrl.sign,params);
    }

    /**
     *  获取反馈列表
     * @return
     */
    public Observable<JSONObject>  getSpaceDetail(){
        HashMap<String,String> params = new HashMap<>();
        return post(ApiUrl.spaceLog,params);
    }

    //public Observable<String> uploadUserHeadRequest(File filePath) {
    //    return upload(ApiUrl.uploadHead)
    //        .addMultipartParameter(getCommonParasm())
    //        .addMultipartFile("img", filePath)
    //        .setPriority(Priority.IMMEDIATE)
    //        .build()
    //        .getStringObservable();
    //}



}
