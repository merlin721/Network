package com.soyoung.component_base.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.os.Environment;
import com.soyoung.component_base.util.constant.Constants;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapUtils {

  public static final int minSize = 1080;

  public static final int minSampleHeight = 2000;
  /**
   * 图片大于1024K则压缩。
   */
  private static final int imageMaxSize = 3 * 500 * 1024;
  /**
   * 视频压缩第一帧的bitmap
   */
  public static String getPostVideoBiampPath(String filePath) {
    File file = new File(filePath);
    filePath = file.getName();

    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(Constants.POST_VIDEO_DIR);
    int sub_str_index = filePath.lastIndexOf(".");
    String sub_str = filePath.substring(0, sub_str_index);
    stringBuffer.append(sub_str).append("bitmap_thumb").append(".png");

    return stringBuffer.toString();
  }

  public static boolean saveBitmapFile(Bitmap bitmap, String toPath) {
    boolean isSuccess = true;
    File file = new File(toPath);//将要保存图片的路径
    try {
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
      bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
      bos.flush();
      bos.close();
    } catch (IOException e) {
      isSuccess = false;
    } catch (Exception ex) {
      isSuccess = false;
    }
    return isSuccess;
  }

  /**
   * 获取压缩好的路径(mp4)
   */
  public static String getCompressVideoOutpath(String filePath) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(Constants.POST_VIDEO_DIR);
    return stringBuffer.toString();
  }

  //生成压缩文件
  public static String getCompressImageNew(String path, String newPath) {
    try {
      File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/sytmp");
      if (!f.exists()) {
        f.mkdir();
      }

      File paster =
          new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/sypaster");
      if (!paster.exists()) {
        paster.mkdir();
      }

      File file = new File(newPath);

      FileOutputStream out = new FileOutputStream(file);
      out.write(getImageByPath(path, false).toByteArray());
      out.flush();
      out.close();
      return newPath;
    } catch (Exception e) {
      //CrashReport.postCatchedException(new Exception("BitmapUtils 316 getCompressImageNew is error"));
      //CrashReport.postCatchedException(e);
      return path;
    }
  }

  /**
   * <图片按比例大小压缩方法（根据路径获取图片并压缩）> <功能详细描述>
   *
   * @see [类、类#方法、类#成员]
   */
  public static ByteArrayOutputStream getImageByPath(String srcPath, boolean isNeedTime) {
    Bitmap bitmap = null;
    String time = null;
    try {
      BitmapFactory.Options newOpts = new BitmapFactory.Options();
      newOpts.inPurgeable = true; // Tell to gc that whether it needs free
      newOpts.inInputShareable = true; // Which kind of reference will be
      newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
      // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
      newOpts.inJustDecodeBounds = true;
      bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
      newOpts.inJustDecodeBounds = false;
      int width = newOpts.outWidth;
      int height = newOpts.outHeight;
      // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
      float minHeight = minSize;
      // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
      int be = 1;// be=1表示不缩放
      if (width >= height && width >= minHeight) {
        // 如果宽度大的话根据宽度固定大小缩放
        be = (int) (newOpts.outWidth / minHeight);
        newOpts.outWidth = (int) minSize;
        newOpts.outHeight = height / be;// (int)(minSize*be);
      } else if (width < height && height > minHeight) {
        // 如果高度高的话根据宽度固定大小缩放
        if (newOpts.outHeight > minSampleHeight)//大于2000
        {
          be = (int) (newOpts.outHeight / minSampleHeight);
          newOpts.outHeight = (int) minSampleHeight;
          newOpts.outWidth = width / be;// (int)(minSize*be);
        } else {
          be = (int) (newOpts.outHeight / minHeight);
          newOpts.outHeight = (int) minSize;
          newOpts.outWidth = width / be;// (int)(minSize*be);
        }
      }
      if (be <= 0) {
        be = 1;
      }
      // newOpts.inSampleSize = be;// 设置缩放比例

      // bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

      // ///////////////////////////////////////////////////////////
      File f = new File(srcPath);
      FileInputStream fin = new FileInputStream(f);
      FileDescriptor fd = fin.getFD();

      BitmapFactory.decodeFileDescriptor(fd, null, newOpts);
      //            int compute = BitmapUtil.computeSampleSize(newOpts, minSize, imageMaxSize);
      newOpts.inSampleSize = be;
      newOpts.inJustDecodeBounds = false;
      BitmapFactory.decodeStream(fin, null, newOpts);
      System.err.println("getImageByPath====width===="
          + width
          + "  "
          + newOpts.outWidth
          + "======height=="
          + height
          + " "
          + newOpts.outHeight
          + "============"
          + be
          + "===========compute==");
      // ///////////////////////////////////////////////////////////

      // 获取图片的生成时间
      ExifInterface exif = new ExifInterface(srcPath);
      time = exif.getAttribute(ExifInterface.TAG_DATETIME);
      if (time == null || "".equals(time)) {
        // 取不到照片的创建时间，则取照片的最后修改时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        File file = new File(srcPath);
        Date date = new Date(file.lastModified());
        time = GeneralUtils.splitToSecond(dateFormat.format(date));
      } else {
        time = GeneralUtils.splitToPhotoTime(time);
      }
      bitmap = ImageUtils.rotaingImageView(ImageUtils.getDegree(srcPath),
          BitmapFactory.decodeFile(srcPath, newOpts));
      if (bitmap == null) {
        return null;
      }
    } catch (Exception e) {
      //CrashReport.postCatchedException(new Exception("BitmapUtils 119 line getImageByPath error"));
      //CrashReport.postCatchedException(new Exception(e));
      return null;
    }
    return compressImage(bitmap, time, isNeedTime, srcPath);// 压缩好比例大小后再进行质量压缩
  }

  /**
   * <质量压缩方法,并且添加时间水印> <功能详细描述>
   *
   * @param isNeedTime 是否需要添加水印
   * @see [类、类#方法、类#成员]
   */
  public static ByteArrayOutputStream compressImage(Bitmap image, String date, boolean isNeedTime,
      String srcPath) {
    try {
      if (image != null) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitMap = image;
        if (isNeedTime) {
          bitMap =
              Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
          String time = "拍摄于:" + date;
          Canvas canvasTemp = new Canvas(bitMap);
          canvasTemp.drawColor(Color.WHITE);
          Paint p = new Paint();
          String familyName = "宋体";// 设置水印字体
          Typeface font = Typeface.create(familyName, Typeface.BOLD);
          p.setColor(Color.RED);// 设置水印字体颜色
          p.setTypeface(font);
          p.setTextSize(30);
          canvasTemp.drawBitmap(image, 0, 0, p);
          canvasTemp.drawText(time, image.getWidth() / 2 - p.measureText(time) / 2,
              image.getHeight() * 9 / 10, p);
          canvasTemp.save();
          canvasTemp.restore();
          image.recycle();
          // 添加水印文字
        }
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length > imageMaxSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
          baos.reset();// 重置baos即清空baos
          bitMap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
          options -= 5;// 每次都减少5
        }
        bitMap.recycle();
        System.gc();
        return baos;
      } else {
        return null;
      }
    } catch (Exception e) {
      //CrashReport.postCatchedException(new Exception("BitmapUtils 276 line compressImage error"));
      return null;
    }
  }

}
