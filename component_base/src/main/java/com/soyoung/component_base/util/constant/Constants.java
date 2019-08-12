package com.soyoung.component_base.util.constant;

import android.os.Environment;

/**
 * 全局參數
 * by huitailang
 */
public class Constants {

  //视频压缩的参数(比特率，参数越大，压缩质量就越好，同样压缩时间就越快)
  public static final int VIDEO_BITRATE = 1800 * 1000;
  //视频压缩每秒质量
  public static final int SECOND_COMPRESS_DATA = 500;
  //视频压缩秒数倍数
  public static final float SECOND_COMPRESS_MULTIPLE = 2.5f;

  public static String POST_VIDEO_DIR;
  public static String AUDIO_VIDEO_DIR;

  //行距
  public static final int OFFSET = 8;

  static {
    POST_VIDEO_DIR =
        Environment.getExternalStorageDirectory().getAbsolutePath() + "/soyoung_doctor/postvideo/";
    AUDIO_VIDEO_DIR =
        Environment.getExternalStorageDirectory().getAbsolutePath() + "/soyoung_doctor/postaudio/";
  }
}
