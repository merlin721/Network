package com.soyoung.component_base.lisener;

/**
 * 录音回调
 */
public interface RecoderLisener {
  void getTex(String text);

  void getEndTex(String text);

  void getOver(boolean isByUser);

  void SpeechEnd(boolean isByUser);

  void getStart();

  void getError();
}
