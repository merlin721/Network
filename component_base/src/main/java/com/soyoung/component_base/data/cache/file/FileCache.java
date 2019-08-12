package com.soyoung.component_base.data.cache.file;

import android.text.TextUtils;

import com.soyoung.component_base.util.FileUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
  *FileCache
  *@author ：daiwenbo
  *@Time   ：2018/8/28 上午11:09
  *@e-mail ：daiwwenb@163.com
  *@description ：文件缓存
  */
public final class FileCache {
    private final String cache;

    private FileCache() {
         cache = FileUtils.getCacheFilePath("cache");
    }

    private static class FileCacheLoader {
        private static final FileCache INSTANCE = new FileCache();
    }

    public static FileCache getInstance() {
        return FileCacheLoader.INSTANCE;
    }

    public String getCacheDir() {
        return cache;
    }

    /**
     * 创建文件
     */
    public File createCacheFile(String key) {
        File file = new File(cache);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, key + ".txt");
    }

    public synchronized void put(String key, JSONObject jsonObject) {
        putContent(key, jsonObject.toString());
    }

    public synchronized void put(String key, JSONArray jsonArray) {
        putContent(key, jsonArray.toString());
    }

    public synchronized void put(String key, String content) {
        putContent(key, content);
    }

    public synchronized JSONObject getJSONObject(String key) {
        return getAsJSONObject(key);
    }

    public synchronized JSONArray getJSONArray(String key) {
        return getAsJSONArray(key);
    }

    /***
     * 写入数据
     * */
    public void putContent(String key, String value) {
        File file = createCacheFile(key);
        BufferedSink buffer = null;
        try {
            Sink sink = Okio.sink(file);
            buffer = Okio.buffer(sink);
            buffer.writeUtf8(value);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(buffer);
        }
    }

    private JSONObject getAsJSONObject(String key) {
        String JSONString = getAsString(key);
        if (null != JSONString) {
            try {
                return new JSONObject(JSONString);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }


    private JSONArray getAsJSONArray(String key) {
        String JSONString = getAsString(key);
        if (null != JSONString) {
            try {
                return new JSONArray(JSONString);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 缓存中是否有数据
     * @return
     */
    public boolean isNull(){
        String buyTruckPrice = getAsString("buyTruckPrice");
        String truckMileage = getAsString("truckMileage");
        if(TextUtils.isEmpty(buyTruckPrice) &&TextUtils.isEmpty(truckMileage)){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 读取 String数据
     *
     * @param key
     * @return String 数据
     */
    private String getAsString(String key) {
        File file = createCacheFile(key);
        if (!file.exists())
            return null;
        BufferedSource buffer = null;
        try {
            Source source = Okio.source(file);
            buffer = Okio.buffer(source);
            return buffer.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeQuietly(buffer);
        }
    }



    /**
     * 关闭流
     */
    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();

            } catch (RuntimeException rethrown) {

                throw rethrown;

            } catch (Exception ignored) {
            }
        }

    }

    /**
     * 获取condition  key-value 数组
     * @param key jsonArray key
     * @return
     */
    public ArrayList<String> getKeyListFromCondition(String key) {
        ArrayList<String> list = new ArrayList<>();
        JSONArray array = getAsJSONArray(key);
        if (null != array) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.optJSONObject(i);
               String value=json.optString("value");
                list.add(value);
            }
            return list;
        } else return null;

    }

    /**
     * key  value 形式 通过value  获取key
     *
     * @param jsonKey 要获取的jsonArray
     * @param value   要获取key的value
     * @return
     */
    public String getIdFromConditionByValue(String jsonKey, String value) {
        HashMap<String, String> map = new HashMap<>();
        JSONArray array = getAsJSONArray(jsonKey);

        if (null != array) {
            for (int i = 0; i < array.length(); i++) {

                JSONObject json = array.optJSONObject(i);
                map.put(json.optString("value"), json.optString("key"));
            }
            return map.get(value).toString();
        } else return "";


    }

}
