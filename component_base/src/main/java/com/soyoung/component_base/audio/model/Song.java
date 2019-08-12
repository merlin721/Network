package com.soyoung.component_base.audio.model;

import java.io.Serializable;

/**
 * 音频信息
 */
public class Song implements Serializable {
  public String displayName;
  public String path;
  public String uploadPath;
  public int duration;
  public int currentDuration;
  public int size;
}
