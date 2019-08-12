package com.soyoung.component_base.widget;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.soyoung.component_base.R;
import com.soyoung.component_base.audio.model.Song;
import com.soyoung.component_base.audio.player.IPlayback;
import com.soyoung.component_base.audio.player.Player;
import com.soyoung.component_base.lisener.BaseOnClickListener;
import com.soyoung.component_base.lisener.IPostAudioPlay;
import com.soyoung.component_base.lisener.IPostDelete;
import com.soyoung.component_base.lisener.IPostFocus;
import com.soyoung.component_base.lisener.IPostImgClick;
import com.soyoung.component_base.lisener.IPostTextChangerLisener;
import com.soyoung.component_base.util.LogUtils;
import com.soyoung.component_base.util.TimeUtils;

/**
 * Created by huitailang on 16/8/23.
 */
public class CustomPostAudio extends APostParent {
  public EditText describe_next_edit;//description layout
  public EditText describe_edit;//description layout
  private LinearLayout audio_main_layout;
  private LinearLayout audioLayout;
  private TextView current, total;
  private ImageView mPlayView;
  public SeekBar seekBarProgress;
  private ImageView audio_more;

  private IPostDelete deleInter;
  private IPostFocus postFocus;
  private IPostTextChangerLisener postTextChangerLisener;
  public Song song;

  private String mImgPath = "";//audio 本地路径
  private String mImgUploadPath = "";//audio 上传路径
  public boolean mIsPlay;

  private static final long UPDATE_PROGRESS_INTERVAL = 1000;
  private IPostAudioPlay iPostAudioPlay;
  private IPlayback playback;
  private Handler mHandler = new Handler();
  private Runnable mProgressCallback = new Runnable() {
    @Override
    public void run() {
      if (playback.isPlaying()) {
        int progress = (int) (seekBarProgress.getMax()
            * ((float) playback.getProgress() / (float) getCurrentSongDuration()));
        LogUtils.eTag("zq---progress", progress + "");
        updateProgressTextWithDuration(playback.getProgress());
        if (progress >= 0 && progress <= seekBarProgress.getMax()) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            seekBarProgress.setProgress(progress, true);
          } else {
            seekBarProgress.setProgress(progress);
          }
          mHandler.postDelayed(this, UPDATE_PROGRESS_INTERVAL);
        }
      }
    }
  };

  private void updateProgressTextWithDuration(int duration) {
    setCurrentText(TimeUtils.formatDuration(duration));
    setCurrentProgress(duration);
  }

  private int getCurrentSongDuration() {
    Song currentSong = playback.getPlayingSong();
    int duration = 0;
    if (currentSong != null) {
      duration = currentSong.duration;
    }
    return duration;
  }

  public CustomPostAudio(Context context) {
    super(context);
  }

  public CustomPostAudio(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomPostAudio(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  void initView() {
    View.inflate(context, R.layout.layout_custom_post_audio, this);
    setTag(System.currentTimeMillis());
    playback = new Player();
    song = new Song();
    playback.registerCallback(new IPlayback.Callback() {
      @Override public void onComplete(@Nullable Song next) {
        setPlayStatueUi(false);
      }

      @Override public void onPlayStatusChanged(boolean isPlaying) {
        setPlayStatueUi(isPlaying);
        if (isPlaying) {
          mHandler.removeCallbacks(mProgressCallback);
          mHandler.post(mProgressCallback);
        } else {
          mHandler.removeCallbacks(mProgressCallback);
        }
      }
    });
    //song.path = Environment.getExternalStorageDirectory() + "/soyoung_doctor/" + getTag() + ".wav";
    audioLayout = findViewById(R.id.audio_layout);//video layout
    audio_main_layout = findViewById(R.id.audio_main_layout);
    audio_main_layout.setVisibility(View.GONE);
    current = findViewById(R.id.current);
    total = findViewById(R.id.total);
    describe_edit = findViewById(R.id.describe_edit);//mark edit
    describe_next_edit = findViewById(R.id.describe_next_edit);//description next edit
    describe_next_edit.setVisibility(View.GONE);
    mPlayView = findViewById(R.id.play_img);
    seekBarProgress = findViewById(R.id.audio_seek_progress);
    audio_more = findViewById(R.id.audio_more);
    audio_more.setOnClickListener(new BaseOnClickListener() {
      @Override public void onViewClick(View v) {
        deleInter.delete(Long.parseLong(String.valueOf(getTag())), APostParent.POST_TYPE_AUDIO);
      }
    });
    ////初始值
    //audioLayout.setVisibility(View.GONE);
    //describe_next_edit.setVisibility(View.GONE);
    //audio_main_layout.setBackgroundResource(0);
    //describe_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    //  @Override
    //  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    //    if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE || (
    //        event != null
    //            && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
    //            && KeyEvent.ACTION_DOWN == event.getAction())) {
    //    }
    //    return false;
    //  }
    //});

    describe_edit.setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        describe_edit.setEnabled(hasFocus ? true : false);
      }
    });
    //describe_edit.addTextChangedListener(new TextWatcher() {
    //  @Override
    //  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    //
    //  }
    //
    //  @Override
    //  public void onTextChanged(CharSequence s, int start, int before, int count) {
    //
    //  }
    //
    //  @Override
    //  public void afterTextChanged(Editable s) {
    //  }
    //});
    describe_next_edit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        if (null != postTextChangerLisener) {
          postTextChangerLisener.change();
        }
      }
    });
    //text
    describe_next_edit.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (postFocus != null) {
          postFocus.focus(v.hasFocus(), v);
        }
      }
    });

    seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
          updateProgressTextWithDuration(progress);
        }
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mProgressCallback);
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        int progressDuration = (int) (getCurrentSongDuration() * ((float) seekBar.getProgress()
            / seekBarProgress.getMax()));
        playback.seekTo(progressDuration);
        if (playback.isPlaying()) {
          mHandler.removeCallbacks(mProgressCallback);
          mHandler.post(mProgressCallback);
        }
      }
    });
    mPlayView.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        //播放
        if (playback.isPlaying()) {
          playback.pause();
          setPlayStatueUi(false);
          iPostAudioPlay.pause(Long.parseLong(getTag().toString()));
        } else {
          playback.play();
          setPlayStatueUi(true);
          iPostAudioPlay.start(Long.parseLong(getTag().toString()));
        }
      }
    });
  }

  public void onPause() {
    if (playback.isPlaying()) {
      playback.pause();
      setPlayStatueUi(false);
    }
  }

  public boolean isPlaying() {
    return playback.isPlaying();
  }

  //設置状态和UI
  public void setPlayStatueUi(boolean isPlaying) {
    mIsPlay = isPlaying;
    mPlayView.setImageResource(
        isPlaying ? R.drawable.audio_pause_btn_drawable : R.drawable.audio_play_btn_drawable);
  }

  public void setAudioVizibility(String text, int vizibility) {
    if (vizibility == View.VISIBLE) {
        setMarkInfo(text);
      describe_next_edit.setText("");
      describe_next_edit.setVisibility(View.VISIBLE);
        describe_next_edit.requestFocus();
      //执行 prepare
      playback.prepare();
    }
    audio_main_layout.setVisibility(vizibility);
  }

  public boolean isAudioVizibility() {
    return (audio_main_layout.getVisibility() == View.VISIBLE ? true : false);
  }

  @Override
  public String getImgPath() {
    return song.path;
  }

  @Override public void setImgPath(String imgPath) {
    song.path = imgPath;
    playback.setPlaySong(song);
  }

  /**
   * 获取文本信息
   */
  public String getText() {
    return TextUtils.isEmpty(describe_next_edit.getText()) ? ""
        : describe_next_edit.getText().toString();
  }

  /**
   * set文本信息
   */
  public void setText(String text) {
    describe_next_edit.setText(text);
    describe_next_edit.setSelection(text.length());
  }

  @Override
  public void setText(SpannableString text) {
    describe_next_edit.setText(text);
  }

  public String getMarkInfo() {
    return describe_edit.getText().toString();
  }

  /**
   * 录制的 语音转文字
   */
  @Override
  public void setMarkInfo(String markInfo) {
    describe_edit.setText(markInfo);
    describe_edit.setSelection(markInfo.length());
  }

  @Override public String getImgUploadPath() {
    return song.uploadPath;
  }

  /**
   * set upload path
   */
  public void setImgUploadPath(String mImgUploadPath) {
    song.uploadPath = mImgUploadPath;
  }

  @Override public EditText getCanEditView() {
    return describe_next_edit;
  }

  public void setFocus() {
    if (describe_next_edit != null) {
      describe_next_edit.requestFocus();
    }
  }

  @Override public void clearFocus() {
    if (null != describe_next_edit) {
      describe_next_edit.clearFocus();
    }
  }

  public boolean isFocus() {
    return describe_next_edit.isFocused();
  }

  /**
   * 返回图片类型
   */
  @Override
  public int getImgType() {
    return POST_TYPE_AUDIO;
  }

  public void setOnImgDelete(IPostDelete deleInter) {
    this.deleInter = deleInter;
  }

  @Override public void setOnImgClick(IPostImgClick deleInter) {

  }

  @Override
  public void setOnPostFocus(IPostFocus postFocus) {
    this.postFocus = postFocus;
  }

  @Override public void setOnPostTextChange(IPostTextChangerLisener postTextChange) {
    this.postTextChangerLisener = postTextChange;
  }

  public void setAudioPlayLisener(IPostAudioPlay iPostAudioPlay) {
    this.iPostAudioPlay = iPostAudioPlay;
  }


  public void setDuration(int totalDuration) {
    song.duration = totalDuration;
  }

  public int getDuration() {
    if (null == song) {
      return 0;
    }
    return song.duration;
  }

  private int getDuration(int progress) {
    return (int) (song.duration * ((float) progress / seekBarProgress.getMax()));
  }

  public void setCurrentText(String text) {
    current.setText(text);
  }

  public void setCurrentProgress(int progress) {
    song.currentDuration = progress;
  }

  public void setCurrentText(int progress) {
    current.setText(TimeUtils.formatDuration(progress));
  }

  public void setTotalText(String text) {
    total.setText(text);
  }

  public void ondestory() {
    mHandler.removeCallbacks(mProgressCallback);
    mHandler.removeCallbacksAndMessages(null);
    if (playback.isPlaying()) {
      playback.pause();
      setPlayStatueUi(false);
    }
    playback.releasePlayer();
  }
}
