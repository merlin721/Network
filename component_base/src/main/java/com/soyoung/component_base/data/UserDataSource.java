package com.soyoung.component_base.data;

import android.text.TextUtils;


import com.soyoung.component_base.data.cache.sp.AppPreferencesHelper;
import com.soyoung.component_base.data.entity.UserEntity;
import com.soyoung.component_base.util.FileUtils;
import com.soyoung.component_base.util.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * UserDataSource
 *
 * @author ：daiwenbo
 * @Time ：2018/6/25 下午7:23
 * @e-mail ：daiwwenb@163.com
 * @description ：用户数据管理类
 */
public final class UserDataSource {

    private UserEntity mUserInfo;

    private UserDataSource() {
        String user_id = AppPreferencesHelper.getString(AppPreferencesHelper.USER_ID);
        if (!TextUtils.isEmpty(user_id)) {
            mUserInfo = readUserDataFile();
            LogUtils.e("UserDataSource(UserDataSource.java:32)"+"初始化"+user_id);
        }
    }

    public static UserDataSource getInstance() {
        return LoginDataCenterLoader.INSTANCE;
    }

    public void cleanUser() {
        AppPreferencesHelper.remove(AppPreferencesHelper.USER_ID);
        this.mUserInfo = null;
        File userDirFile = null;
        try {
            userDirFile = getUserDirFile();
            FileUtils.deleteAllInDir(userDirFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public UserEntity getUser() {
        if (null == mUserInfo) {
            mUserInfo = new UserEntity();
        }
        return mUserInfo;
    }

    public void setUser(UserEntity user) {
        String uid = user.getUid();
        mUserInfo = user;
        if (!TextUtils.isEmpty(uid) && !"0".equals(uid)) {
            AppPreferencesHelper.put(AppPreferencesHelper.USER_ID, uid);
        }
        writeUserDataFile(user);
    }

    public String getUid() {
        return AppPreferencesHelper.getString(AppPreferencesHelper.USER_ID);
    }

    /**
     * 将user对象序列化到文件中
     */
    private void writeUserDataFile(UserEntity user) {
        FileOutputStream out;
        ObjectOutputStream objectOutputStream;
        try {
            File userDirFile = getUserDirFile();
            out = new FileOutputStream(userDirFile);
            objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把文件中的对象信息读取出来
     */
    private UserEntity readUserDataFile() {
        FileInputStream inputStream;
        ObjectInputStream objectInputStream;
        UserEntity userInfo = null;
        try {
            File userDirFile = getUserDirFile();
            inputStream = new FileInputStream(userDirFile);
            objectInputStream = new ObjectInputStream(inputStream);
            userInfo = (UserEntity) objectInputStream.readObject();
            objectInputStream.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LogUtils.e("readUserDataFile(UserDataSource.java:115)" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("readUserDataFile(UserDataSource.java:118)" + e.getMessage());
        }
        return userInfo;
    }

    /**
     * 创建存储user对象的文件目录
     */
    private File getUserDirFile() throws IOException {
        File diskCacheDirFile = FileUtils.getDiskCacheDirFile();
        String path = diskCacheDirFile + File.separator + "user";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File userFile = new File(file.getAbsolutePath(), "user.txt");
        if (!userFile.exists()) {
            userFile.createNewFile();
        }

        return userFile;
    }

    private static class LoginDataCenterLoader {
        private static final UserDataSource INSTANCE = new UserDataSource();
    }
}