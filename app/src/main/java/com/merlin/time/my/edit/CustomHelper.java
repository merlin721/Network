package com.merlin.time.my.edit;

//import android.net.Uri;
//import android.os.Environment;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.RadioGroup;
//
//import com.merlin.time.R;
//import org.devio.takephoto.app.TakePhoto;
//import org.devio.takephoto.compress.CompressConfig;
//import org.devio.takephoto.model.CropOptions;
//import org.devio.takephoto.model.LubanOptions;
//import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;

/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.devio.org
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class CustomHelper {

    //public static CustomHelper of() {
    //    return new CustomHelper();
    //}
    //
    //public CustomHelper() {
    //    init();
    //}
    //
    //private void init() {
    //
    //}
    //
    //public void onClick(View view, TakePhoto takePhoto) {
    //    File file = new File(Environment.getExternalStorageDirectory(),
    //        "/time/pic" + System.currentTimeMillis() + ".jpg");
    //    if (!file.getParentFile().exists()) {
    //        file.getParentFile().mkdirs();
    //    }
    //    Uri imageUri = Uri.fromFile(file);
    //
    //    configCompress(takePhoto,true);
    //    configTakePhotoOption(takePhoto);
    //    switch (view.getId()) {
    //        case R.id.choose_pic_pic_photo:
    //            int limit = 1;
    //            if (limit > 1) {
    //                takePhoto.onPickMultiple(limit);
    //                return;
    //            }
    //            //                if(rgFrom.getCheckedRadioButtonId()==R.id.rbFile){
    //            //                    if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
    //            //                        takePhoto.onPickFromDocumentsWithCrop(imageUri,getCropOptions());
    //            //                    }else {
    //            //                        takePhoto.onPickFromDocuments();
    //            //                    }
    //            //                    return;
    //            //                }else {
    //            //                    if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
    //            //是否裁剪
    //            takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
    //            //                    }else {
    //            //              takePhoto.onPickFromGallery();
    //            //                    }
    //            //                }
    //            break;
    //        case R.id.choose_pic_take_photo:
    //            //                if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
    //            //                    takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions(true));
    //            //                }else {
    //            takePhoto.onPickFromCapture(imageUri);
    //            //                }
    //            break;
    //        default:
    //            break;
    //    }
    //}
    //
    //private void configTakePhotoOption(TakePhoto takePhoto) {
    //    TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
    //    //使用takephoto自带相册
    //    builder.setWithOwnGallery(true);
    //    //纠正拍照的照片旋转角度
    //    builder.setCorrectImage(true);
    //    takePhoto.setTakePhotoOptions(builder.create());
    //}
    //
    ///**
    // *
    // * @param takePhoto
    // * @param isCompress ture 为压缩
    // */
    //private void configCompress(TakePhoto takePhoto,boolean isCompress) {
    //
    //    if (!isCompress) {
    //        //不压缩
    //        takePhoto.onEnableCompress(null, false);
    //        return;
    //    }
    //    int maxSize = 1500 * 1024;
    //    int width = 1080;
    //    int height = 1920;
    //    boolean showProgressBar =true;
    //    //拍照压缩后是否保存原图
    //    boolean enableRawFile = true;
    //    CompressConfig config;
    //    //压缩工具
    //    //if (rgCompressTool.getCheckedRadioButtonId() == R.id.rbCompressWithOwn) {
    //    //    config = new CompressConfig.Builder().setMaxSize(maxSize)
    //    //        .setMaxPixel(width >= height ? width : height)
    //    //        .enableReserveRaw(enableRawFile)
    //    //        .create();
    //    //} else {
    //        LubanOptions option = new LubanOptions.Builder().setMaxHeight(height)
    //            .setMaxWidth(width)
    //            .setMaxSize(maxSize)
    //            .create();
    //        config = CompressConfig.ofLuban(option);
    //        config.enableReserveRaw(enableRawFile);
    //    //}
    //    takePhoto.onEnableCompress(config, showProgressBar);
    //}
    //
    //private CropOptions getCropOptions() {
    //
    //    int width = 1080;
    //    int height = 1920;
    //    //裁切工具：
    //    boolean withWonCrop =  false;
    //
    //    CropOptions.Builder builder = new CropOptions.Builder();
    //    //尺寸/比例
    //    //if (rgCropSize.getCheckedRadioButtonId() == R.id.rbAspect) {
    //        builder.setAspectX(width).setAspectY(height);
    //    //} else {
    //    //    builder.setOutputX(width).setOutputY(height);
    //    //}
    //    builder.setWithOwnCrop(withWonCrop);
    //    return builder.create();
    //}
}
