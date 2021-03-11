package com.wczx.api.cache.service;

import java.util.List;

/**
 * redis 服务接口
 *
 * @author wj
 */
public interface RedisServiceApi {


    /**
     * redis 的get操作，通过key获取存储在redis中的对象
     *
     * @param prefix key的前缀
     * @param key    业务层传入的key
     * @return 存储于redis中的对象
     */
    String get(String prefix, String key);

    /**
     * redis的set操作
     *
     * @param prefix 键的前缀
     * @param key    键
     * @param value  值
     * @return 操作成功为true，否则为true
     */
    boolean set(String prefix, String key, int expireSeconds, String value);

    /**
     * redis的set操作
     *
     * @param prefix 键的前缀
     * @param key    键
     * @param value  值
     * @return 操作成功为true，否则为true
     */
    boolean setList(String prefix, String key, int expireSeconds, List<String> value);

    /**
     * 判断key是否存在于redis中
     *
     * @param keyPrefix key的前缀
     * @param key
     * @return
     */
    boolean exists(String keyPrefix, String key);

    /**
     * 自增
     *
     * @param keyPrefix
     * @param key
     * @return
     */
    long incr(String keyPrefix, String key);

    /**
     * 自减
     *
     * @param keyPrefix
     * @param key
     * @return
     */
    long decr(String keyPrefix, String key);


    /**
     * 删除缓存中的用户数据
     *
     * @param prefix
     * @param key
     * @return
     */
    boolean delete(String prefix, String key);
}
