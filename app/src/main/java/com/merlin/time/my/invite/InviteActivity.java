package com.merlin.time.my.invite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.time.R;
import com.merlin.time.utils.ExcutorPoolUtils;
import com.merlin.time.utils.SystemUtils;
import com.merlin.time.utils.Util;
import com.merlin.time.utils.WXUtils;
import com.merlin.time.view.TimeTextView;
import com.soyoung.component_base.image.ImageWorker;
import com.soyoung.component_base.mvpbase.BaseActivity;
import com.soyoung.component_base.util.FileUtils;
import com.soyoung.component_base.util.ToastUtils;
import com.soyoung.component_base.util.Utils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author merlin
 * @date 2018年11月11日
 * desc 关于
 */
public class InviteActivity extends BaseActivity {
  private ImageView back;
  private ImageView invite;
  private String url =
      "http://time.taowangzhan.com/api/album/get-swx-invitation-qr-code?token=v_5488285d16428f531404b217fe5ef238_293_1551880499&invitation_code=0&is_save_img=1";
  private TimeTextView session;
  private TimeTextView moment;
  /**
   * 对保存的图片命名
   */
  String name = System.currentTimeMillis() + ".jpg";
  private String imagePath = FileUtils.getCacheFilePath("Camera") + name;

  @Override protected int setLayoutId() {
    return R.layout.activity_invite;
  }

  @Override protected void initView() {
    width = SystemUtils.getDisplayWidth(this);
    back = findViewById(R.id.about_back_img);
    invite = findViewById(R.id.invite_img);
    session = findViewById(R.id.share_dialog_wechat_tv);
    moment = findViewById(R.id.share_dialog_wechat_moment);
  }

  @Override protected void initData(@Nullable Bundle savedInstanceState) {
    initBitmap();
    ImageWorker.loadImage(this, url, invite);
    invite.setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View v) {
        AlertDialog dialog = new AlertDialog.Builder(InviteActivity.this)
            .setMessage("是否保存")
            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
              @Override public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //String path =   getImagePath(url);
                new Thread(new Runnable() {
                  @Override
                  public void run() {
                    String path = getImagePath(url);
                    /**
                     * 拷贝到指定路径
                     */
                    copyFile(path, imagePath);
                    File file = new File(imagePath, name);
                    setMediaDtore(context, name, file);
                    //Intent intentBroadcast = new Intent(
                    //    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    //File file = new File(imagePath);
                    //intentBroadcast.setData(Uri.fromFile(file));
                    //sendBroadcast(intentBroadcast);
                  }
                }).start();
              }
            })
            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
              @Override public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
              }
            })
            .create();
        dialog.show();
        return false;
      }
    });
  }

  private Bitmap bitmap = null;

  private void initBitmap() {

    ExcutorPoolUtils.THREAD_POOL_EXECUTOR.execute(new Runnable() {
      @Override
      public void run() {
        try {

          bitmap = Glide.with(context)
              .asBitmap()
              .load(url)
              .submit(width, 500)
              .get();
        } catch (ExecutionException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
  }

  public void setMediaDtore(Context context, String fileName, File file) {
    try {
      MediaStore.Images.Media.insertImage(Utils.getApp().getContentResolver(),
          file.getAbsolutePath(), fileName, null);
      // 最后通知图库更新
      Utils.getApp().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
          Uri.fromFile(new File(file.getPath()))));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override protected void setListener() {
    Disposable disposable = RxView.clicks(back)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            finish();
          }
        });
    Disposable disposable1 = RxView.clicks(session)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            shareSession(1);
          }
        });
    Disposable disposable2 = RxView.clicks(moment)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            shareSession(2);
          }
        });
  }

  private int width;

  private void shareSession(int type) {
    if (WXUtils.api.isWXAppInstalled()) {
      if (bitmap != null) {
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
        //                                bitmap[0].recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        if (type == 1) {
          req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
          req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        //
        //                      req.userOpenId = getOpenId();
        //调用api接口，发送数据到微信
        WXUtils.api.sendReq(req);
      }
    } else {
      ToastUtils.showToast(this, "请先安装微信");
    }
  }

  private String buildTransaction(final String type) {
    return (type == null) ? String.valueOf(System.currentTimeMillis())
        : type + System.currentTimeMillis();
  }

  /**
   * Glide 获得图片缓存路径
   */
  private String getImagePath(String imgUrl) {
    String path = null;
    FutureTarget<File> future = Glide.with(this)
        .load(imgUrl)
        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    try {
      File cacheFile = future.get();
      path = cacheFile.getAbsolutePath();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return path;
  }

  public void copyFile(String oldPath, String newPath) {
    try {
      int bytesum = 0;
      int byteread = 0;
      File oldfile = new File(oldPath);
      if (oldfile.exists()) { //文件存在时
        InputStream inStream = new FileInputStream(oldPath); //读入原文件
        FileOutputStream fs = new FileOutputStream(newPath);
        byte[] buffer = new byte[1444];
        int length;
        while ((byteread = inStream.read(buffer)) != -1) {
          bytesum += byteread; //字节数 文件大小
          System.out.println(bytesum);
          fs.write(buffer, 0, byteread);
        }
        inStream.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    bitmap.recycle();
    bitmap = null;
  }
}
