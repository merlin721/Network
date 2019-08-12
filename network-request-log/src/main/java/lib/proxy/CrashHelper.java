package lib.proxy;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lib.util.FileUtils;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/6/11
 * description   : 错误日志处理类
 */

public class CrashHelper {
    private static final String TAG=CrashHelper.class.getSimpleName();
    //用来存储设备信息和异常信息
    private HashMap<String, String> infos = new HashMap<>();
    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
    private final String crashFilePath;
    public CrashHelper(Context context) {
        crashFilePath = FileUtils.getCacheFilePath(context, "crash");
        infos=collectDeviceInfo(context);
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public synchronized HashMap<String, String> collectDeviceInfo(Context ctx) {
        HashMap<String, String> infos = new HashMap<>();
        //先把异常加入进去
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //再把设备以及app设备信息加进去
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), 0);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return infos;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    public void saveCrashInfo(String ex) {
        try {
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + ".txt";
            File logFile=new File(crashFilePath,fileName);
            if (!logFile.exists()){
                logFile.createNewFile();
            }
            Log.e(TAG,"saveCrashInfo(CrashHelper.java:99)保存错误日志");
            FileOutputStream fos;
            fos = new FileOutputStream(logFile);
            fos.write(ex.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 处理设备信息
     * */
    @NonNull
    public StringBuffer getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        return sb;
    }

    public String getCrashFilePath() {
        return crashFilePath;
    }
}
