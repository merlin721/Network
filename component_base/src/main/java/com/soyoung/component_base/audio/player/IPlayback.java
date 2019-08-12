package com.soyoung.component_base.audio.player;

import android.support.annotation.Nullable;
import com.soyoung.component_base.audio.model.Song;

public interface IPlayback {

  void setPlaySong(Song song);

  boolean play();

  boolean play(Song song);

  boolean pause();

  boolean isPlaying();

  int getProgress();

  void prepare();

  Song getPlayingSong();

  boolean seekTo(int progress);

  //void setPlayMode(PlayMode playMode);

  void registerCallback(Callback callback);

  void unregisterCallback(Callback callback);

  void removeCallbacks();

  void releasePlayer();

  interface Callback {

    void onComplete(@Nullable Song next);

    void onPlayStatusChanged(boolean isPlaying);
  }
}
