package com.soyoung.component_base.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by sreay tz 14-8-18.
 */
public class PathManager {
	//自定义相机存储路径（图片经过剪裁后的图片，生成640*640）
	public static File getCropPhotoPath() {
		File photoFile = new File(getCropPhotoDir().getAbsolutePath() ,"az"+ createTime()+"_"+"123456"+getMyRandom()+"NWM"+".jpg");
		return photoFile;
	}
	//自定义Webview相机存储路径（图片经过剪裁后的图片，生成640*640）
	public static File getWVCropPhotoPath() {
		File photoFile = new File(getCropPhotoDir().getAbsolutePath() ,"az"+ createTime()+"_"+"123456"+getMyRandom()+"NWM"+".jpg");
		return photoFile;
	}
	public static String getQiNinPath(){
		return "az"+ createTime()+"_"+"123456"+getMyRandom()+"NWM"+".jpg";
	}
	/**
	 * 创建目标文件
	 *
	 * @param uniqueName 文件路径名
	 * @return
	 */
	public static File getDiskCacheDir(String uniqueName) {
		String cachePath = null;
		String path=null;
		File file = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			File externalCacheDir = Utils.getApp().getExternalFilesDir(null);
			if(externalCacheDir!=null){
				cachePath=externalCacheDir.getAbsolutePath();
			}
		}
		if(cachePath==null){
			File filesDir = Utils.getApp().getFilesDir();
			if(filesDir==null){
				File cacheDir =  Utils.getApp().getCacheDir();
				cachePath = cacheDir.getAbsolutePath();
			}else {
				cachePath=filesDir.getAbsolutePath();
			}
		}
		if (!TextUtils.isEmpty(uniqueName)) {
			path = cachePath + File.separatorChar + uniqueName;
		} else {
			path = cachePath;
		}
		file=new File(path);
		return  file;
	}
	//存储剪裁后的图片的文件夹
	public static File getCropPhotoDir() {
		File file = getDiskCacheDir( "/woniu/crop/");
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	public static String getCropPhotoName(String str){
		return str.replace(getCropPhotoDir().getAbsolutePath(),"");
	}


	public static String createTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		Date date = new Date(System.currentTimeMillis());
		String filename = format.format(date);
		return filename;
	}

	/**
	 * 获取一个随机的uuid
	 * @return
	 *
	 */
	public static String getMyUUID(){
		UUID uuid = UUID.randomUUID();
		String uniqueId = uuid.toString();
		return uniqueId;
	}

	/**
	 * 随机生成两位数
	 * @return
     */
	public static int getMyRandom(){
		Random r=new Random();
	//	ToastUtils.showCenter(r.nextInt(89)+10+"");
		return r.nextInt(89)+10;
	}

}
