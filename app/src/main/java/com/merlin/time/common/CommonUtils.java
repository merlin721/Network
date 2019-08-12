package com.merlin.time.common;

import android.content.Context;
import java.text.DecimalFormat;
//import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * @author zhouyang
 * @date 2018/10/28
 * @desc
 */
public class CommonUtils {

    public static String EVENT_CHANGE_NAME = "change_name";
    public static int  EVENT_CHANGE_NAME_CODE = 10;
    public static int  EVENT_CHANGE_PIC_CODE = 11;

    public static String Tencent_APP_ID = "101550184";
//    public static void showShare(String platform,Context context) {
//        final OnekeyShare oks = new OnekeyShare();
//        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
//        if (platform != null) {
//            oks.setPlatform(platform);
//        }
//
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle("时间瓶");
//        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
//        oks.setTitleUrl("http://www.baidu.com");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("时间瓶");
//        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("https://test.taowangzhan.com/app_upload/img/648592dc7/175_175/648592dc79ba122988c1a764ade266a2.jpg");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://www.baidu.com");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("时间瓶时间瓶");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("baidu");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://www.baidu.com");
//
//        //启动分享
//        oks.show(context);
//    }

    /**
     *
     * @param size  b
     */
    public static String setSize(long size) {
        //获取到的size为：1705230
        int GB = 1024 * 1024 * 1024;//定义GB的计算常量
        int MB = 1024 * 1024;//定义MB的计算常量
        int KB = 1024;//定义KB的计算常量
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "G";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "M";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "K";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }
}
