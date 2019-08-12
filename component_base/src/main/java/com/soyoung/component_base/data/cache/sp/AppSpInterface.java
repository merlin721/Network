package com.soyoung.component_base.data.cache.sp;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.Set;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/3/8
 * description   : xxxx描述
 */

 interface AppSpInterface {
    /**
     * SP中写入String
     *
     * @param key   键
     * @param value 值
     */
    void put(@NonNull String key, @NonNull String value);

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    String getString(@NonNull String key);

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    String getString(@NonNull String key, @NonNull String defaultValue);

    /**
     * SP中写入int
     *
     * @param key   键
     * @param value 值
     */
     void put(@NonNull String key, int value);

    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
     int getInt(@NonNull String key);

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
     int getInt(@NonNull String key, int defaultValue);

    /**
     * SP中写入long
     *
     * @param key   键
     * @param value 值
     */
     void put(@NonNull String key, long value);

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
     long getLong(@NonNull String key);

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
     long getLong(@NonNull String key, long defaultValue);

    /**
     * SP中写入float
     *
     * @param key   键
     * @param value 值
     */
     void put(@NonNull String key, float value);

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
     float getFloat(@NonNull String key);

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
     float getFloat(@NonNull String key, float defaultValue);

    /**
     * SP中写入boolean
     *
     * @param key   键
     * @param value 值
     */
     void put(@NonNull String key, boolean value);

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
     boolean getBoolean(@NonNull String key);

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
     boolean getBoolean(@NonNull String key, boolean defaultValue);

    /**
     * SP中写入String集合
     *
     * @param key    键
     * @param values 值
     */
     void put(@NonNull String key, @NonNull Set<String> values);

    /**
     * SP中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
     Set<String> getStringSet(@NonNull String key);
    /**
     * SP中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
     Set<String> getStringSet(@NonNull String key, @NonNull Set<String> defaultValue);

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
     Map<String, ?> getAll();

    /**
     * SP中移除该key
     *
     * @param key 键
     */
     void remove(@NonNull String key);

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
     boolean contains(@NonNull String key);

    /**
     * SP中清除所有数据
     */
     void clear();

}
