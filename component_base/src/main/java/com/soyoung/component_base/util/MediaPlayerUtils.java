package com.soyoung.component_base.util;

import android.media.MediaPlayer;
import java.io.File;
import java.io.FileInputStream;

/**
 * MediaPlayer的工具类
 * Created by 56417 on 2017/9/12.
 */

public class MediaPlayerUtils {

  public static long getRingDuring(String mUri) {
    long duration = 0;
    File file = new File(mUri);
    MediaPlayer mediaPlayer = new MediaPlayer();
    try {
      FileInputStream fis = new FileInputStream(file);
      mediaPlayer.setDataSource(fis.getFD());
      mediaPlayer.prepare();
      duration = mediaPlayer.getDuration();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      mediaPlayer.release();
    }
    return duration;
  }
}
