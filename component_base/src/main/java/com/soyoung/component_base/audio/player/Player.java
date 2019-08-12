package com.soyoung.component_base.audio.player;

import android.media.MediaPlayer;
import android.text.TextUtils;
import com.soyoung.component_base.audio.model.Song;
import java.io.IOException;

/**
 * 播放器的核心
 */
public class Player implements IPlayback, MediaPlayer.OnCompletionListener {
  private static final String TAG = "Player";

  //private static volatile Player sInstance;

  private MediaPlayer mPlayer;

  private Song song;
  // Default size 2: for service and UI
  private Callback callback;
  // Player status
  private boolean isPaused;

  public Player() {
    mPlayer = new MediaPlayer();
    mPlayer.setLooping(false);
    mPlayer.setOnCompletionListener(this);
  }

  //public static Player getInstance() {
  //  if (sInstance == null) {
  //    synchronized (Player.class) {
  //      if (sInstance == null) {
  //        sInstance = new Player();
  //      }
  //    }
  //  }
  //  return sInstance;
  //}

  @Override
  public void setPlaySong(Song song) {
    this.song = song;
  }

  @Override
  public boolean play() {
    if (isPaused) {
      mPlayer.start();
      notifyPlayStatusChanged(true);
      return true;
    }
    try {
      mPlayer.reset();
      mPlayer.setDataSource(song.path);
      mPlayer.prepare();
      mPlayer.start();
      notifyPlayStatusChanged(true);
    } catch (IOException e) {
      notifyPlayStatusChanged(false);
      return false;
    }
    return true;
  }

  @Override public boolean play(Song tempSong) {
    if (isPaused) {
      if (null != song && tempSong.path.equals(song.path))//播放路径一致
      {
        mPlayer.start();
      } else {
        song = tempSong;
        try {
          mPlayer.reset();
          mPlayer.setDataSource(song.path);
          mPlayer.prepareAsync();
          mPlayer.start();
          if (0 != tempSong.currentDuration) {
            mPlayer.seekTo(tempSong.currentDuration);
          }
          notifyPlayStatusChanged(true);
        } catch (IOException e) {
          notifyPlayStatusChanged(false);
          return false;
        }
      }
      return true;
    }
    try {
      song = tempSong;
      mPlayer.reset();
      mPlayer.setDataSource(song.path);
      mPlayer.prepare();
      mPlayer.start();
      notifyPlayStatusChanged(true);
    } catch (IOException e) {
      notifyPlayStatusChanged(false);
      return false;
    }
    return true;
  }

  @Override
  public boolean pause() {
    if (mPlayer.isPlaying()) {
      mPlayer.pause();
      isPaused = true;
      notifyPlayStatusChanged(false);
      return true;
    }
    return false;
  }

  @Override
  public boolean isPlaying() {
    return mPlayer.isPlaying();
  }

  @Override
  public int getProgress() {
    return mPlayer.getCurrentPosition();
  }

  /**
   * prepare
   */
  @Override public void prepare() {
    try {
      mPlayer.reset();
      mPlayer.setDataSource(song.path);
      mPlayer.prepare();
      mPlayer.start();
      mPlayer.pause();
      isPaused = true;
      notifyPlayStatusChanged(false);
    } catch (IOException e) {
      isPaused = false;
      notifyPlayStatusChanged(false);
    }
  }

  @Override public Song getPlayingSong() {
    return song;
  }

  @Override
  public boolean seekTo(int progress) {

    if (song != null) {
      if (song.duration <= progress) {
        onCompletion(mPlayer);
      } else {
        mPlayer.seekTo(progress);
      }
      return true;
    }
    return false;
  }

  @Override
  public void onCompletion(MediaPlayer mp) {
    isPaused = false;
    mPlayer.reset();
    notifyComplete(song);
  }

  @Override
  public void releasePlayer() {
    if (mPlayer.isPlaying()) {
      mPlayer.pause();
      mPlayer.stop();
    }
    mPlayer.release();
    mPlayer = null;
  }

  // Callbacks

  @Override
  public void registerCallback(Callback callback) {
    this.callback = callback;
  }

  @Override
  public void unregisterCallback(Callback callback) {
  }

  @Override
  public void removeCallbacks() {
  }

  private void notifyPlayStatusChanged(boolean isPlaying) {
    this.callback.onPlayStatusChanged(isPlaying);
  }

  private void notifyComplete(Song song) {
    this.callback.onComplete(song);
  }
}
