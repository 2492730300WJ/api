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
     * @param key    业务层传入的key
     * @return 存储于redis中的对象
     */
    String get(String key);

    /**
     * redis的set操作
     * @param key    键
     * @param value  值
     * @return 操作成功为true，否则为true
     */
    boolean set(String key, int expireSeconds, String value);

    /**
     * redis的set操作
     * @param key    键
     * @param value  值
     * @return 操作成功为true，否则为true
     */
    boolean setList(String key, int expireSeconds, List<String> value);

    /**
     * 判断key是否存在于redis中
     *
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 自增
     * @param key
     * @return
     */
    long incr(String key);

    /**
     * 自减
     * @param key
     * @return
     */
    long decr(String key);


    /**
     * 删除缓存中的用户数据
     * @param key
     * @return
     */
    boolean delete(String key);

    /**
     * set bit
     */
    boolean setBit(String key,String value,Integer offset);

    boolean getBit(String key, Integer offset);
}
