package com.soyoung.component_base.image;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.soyoung.component_base.R;
import com.soyoung.component_base.util.LogUtils;
import com.soyoung.component_base.util.SizeUtils;
import com.soyoung.component_base.util.Utils;

import java.io.File;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * glide 加载图片工具类
 */
public class ImageWorker {
    private static final int radius = SizeUtils.dp2px(Utils.getApp(),4);
    public static final String TAG = ImageWorker.class.getSimpleName();

    private static boolean isDestroy(Context context) {
        if (context == null) {
            return true;
        }
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return true;
        }

        return false;
    }
    public static void loadRadiusImage(Context context, String url, ImageView view){
        if (isDestroy(context)) {
            return;
        }
        if (TextUtils.isEmpty(url)){
            view.setBackgroundResource(R.drawable.test_max_img);
            return;
        }
        if (url.endsWith("gif")) {
            GlideApp.with(context)
                    .asGif()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transforms(new CenterCrop(), new RoundedCornersTransformation(radius, 0))
                    .into(view);
        } else {
            GlideApp.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.default_load_img)
                    .transforms(new CenterCrop(),new RoundedCornersTransformation(radius, 0))
                    .into(view);
        }
    }

    public static void loadImage(Context context, String url, ImageView view){
        if (isDestroy(context)) {
            return;
        }
        if (url.endsWith("gif")) {
            GlideApp.with(context)
                    .asGif()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view);
        } else {
            GlideApp.with(context)
                    .load(url)
                    .placeholder(R.drawable.default_load_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view);
        }
    }
    public static void loadImageCircle(Context context, String url, ImageView view,int resId) {
        if (isDestroy(context)) {
            return;
        }
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .placeholder(resId)
                .error(resId)
                .transform(new CircleCrop())//转化 加载出圆形的图片
                .into(view);
    }
    public static void loadImageCircle(Context context, String url, ImageView view) {
       loadImageCircle(context,url,view,R.drawable.icon_default_head);
    }
    public static void imageLoaderGif(Context context, String url, ImageView view) {
        if (isDestroy(context)) {
            return;
        }
        GlideApp.with(context).clear(view);
        GlideApp.with(context)
                .asGif()
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void imageLoaderGif(Context context, int res, ImageView view) {
        if (isDestroy(context)) {
            return;
        }
        GlideApp.with(context).clear(view);
        GlideApp.with(context)
                .asGif()
                .load(res)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void imageLoader(Context context, ImageView view, int id) {
        if (isDestroy(context)) {
            return;
        }
        GlideApp.with(context)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(view);
    }

    public static void imageLoader(Context context, ImageView view, int placeholder, String url) {
        if (isDestroy(context)) {
            return;
        }

        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .load(url)
                    .dontAnimate()
                    .centerCrop()
                    .placeholder(placeholder)//设置默认显示的图片R.drawable.ic_launch z资源文件
                    .into(view);
        }
    }

    /**
     * 获取Bitmap回调
     */
    public static void imageLoaderppBitmap(Context context, String url, ImageView simpleTarget) {
        if (isDestroy(context)) {
            return;
        }

        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .asBitmap()//总是将其转换为Bitmap的对象
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(simpleTarget);
        }
    }

    /**
     * 模糊图片
     */

    public static void imageLoaderBlur(Context context, ImageView view, String url) {
        if (isDestroy(context)) {
            return;
        }

        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .load(url)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new BlurTransformation(20))
                    .into(view);
        }
    }
    public static void imageLoaderRadius(Context context, String url, ImageView view,
                                         int resPlaceHolder, int radius) {
        if (isDestroy(context)) {
            return;
        }

        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(resPlaceHolder)
                    .centerCrop()
                    .transforms(new CenterCrop(), new RoundedCornersTransformation(radius, 0))
                    .into(view);
        } else {
            GlideApp.with(context)
                    .load(resPlaceHolder)
                    .placeholder(resPlaceHolder)
                    .centerCrop()
                    .transforms(new CenterCrop(), new RoundedCornersTransformation(radius, 0))
                    .into(view);
        }
    }

    public static void imageLoaderRadiusMargin(Context context, String url, ImageView view,
                                               int resPlaceHolder, int radius, int margin) {
        if (isDestroy(context)) {
            return;
        }

        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(resPlaceHolder)
                    .centerCrop()
                    .transforms(new CenterCrop(), new RoundedCornersTransformation(radius, margin))
                    .into(view);
        } else {
            GlideApp.with(context)
                    .load(resPlaceHolder)
                    .placeholder(resPlaceHolder)
                    .centerCrop()
                    .transforms(new CenterCrop(), new RoundedCornersTransformation(radius, margin))
                    .into(view);
        }
    }

    public static void imageLoaderBottomRadius(Context context, String url, ImageView view,
                                               int resPlaceHolder, int radius) {
        if (isDestroy(context)) {
            return;
        }

        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(resPlaceHolder)
                    .centerCrop()
                    .transforms(new CenterCrop(), new RoundedCornersTransformation(radius, 0,
                            RoundedCornersTransformation.CornerType.BOTTOM))
                    .into(view);
        } else {
            GlideApp.with(context)
                    .load(resPlaceHolder)
                    .placeholder(resPlaceHolder)
                    .centerCrop()
                    .transforms(new CenterCrop(), new RoundedCornersTransformation(radius, 0,
                            RoundedCornersTransformation.CornerType.BOTTOM))
                    .into(view);
        }
    }
    public static void imageResizeLoader(Context context, File file, ImageView img,
        Transformation transform, int resizeW, int resizeH) {
        if (isDestroy(context)) {
            return;
        }

        if (file == null) {
            return;
        }
        if (transform == null) {
            GlideApp.with(context)
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .override(resizeW, resizeH)
                .into(img);
        } else {
            GlideApp.with(context)
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .transform(transform)
                .override(resizeW, resizeH)
                .into(img);
        }
    }
  public static void imageResizeLoader(Context context, String url, ImageView img,
      Transformation transform, int resizeW, int resizeH) {
    if (isDestroy(context)) {
      return;
    }

    if (TextUtils.isEmpty(url)) {
      return;
    }
    if (transform == null) {
      GlideApp.with(context)
          .load(url)
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .centerCrop()
          .override(resizeW, resizeH)
          .into(img);
    } else {
      GlideApp.with(context)
          .load(url)
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .centerCrop()
          .transform(transform)
          .override(resizeW, resizeH)
          .into(img);
    }
  }

}
