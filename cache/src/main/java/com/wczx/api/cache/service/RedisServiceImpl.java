package com.wczx.api.cache.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.List;

/**
 * redis服务实现
 * @author wj
 */
@Service
public class RedisServiceImpl implements RedisServiceApi {

    /**
     * 通过连接池对象可以获得对redis的连接
     */
    @Resource
    JedisPool jedisPool;

    @Override
    public String get(String prefix, String key) {
        // redis连接
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            // 生成真正的存储于redis中的key
            String realKey = prefix + key;
            // 通过key获取存储于redis中的对象（这个对象是以json格式存储的，所以是字符串）
            String strValue = jedis.get(realKey);
            // 将json字符串转换为对应的对象
            return strValue;
        } finally {
            // 归还redis连接到连接池
            returnToPool(jedis);
        }
    }

    @Override
    public boolean set(String prefix, String key, int expireSeconds, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (value == null) {
                return false;
            }
            // 生成实际存储于redis中的key
            String realKey = prefix + key;
            // 获取key的过期时间
            int expires = expireSeconds;

            if (expires <= 0) {
                // 设置key的过期时间为redis默认值（由redis的缓存策略控制）
                jedis.set(realKey, value);
            } else {
                // 设置key的过期时间
                jedis.setex(realKey, expires, value);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public boolean setList(String prefix, String key, int expireSeconds, List<String> value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            if (value == null || value.size() <= 0) {
                return false;
            }

            // 生成实际存储于redis中的key
            String realKey = prefix + key;
            // 获取key的过期时间
            int expires = expireSeconds;

            if (expires <= 0) {
                // 设置key的过期时间为redis默认值（由redis的缓存策略控制）
                jedis.rpush(realKey, value.toArray(new String[value.size()]));
            } else {
                // 设置key的过期时间
                jedis.rpush(realKey, value.toArray(new String[value.size()]));
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public boolean exists(String keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public long incr(String keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public long decr(String keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public boolean delete(String prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix + key;
            Long del = jedis.del(realKey);
            return del > 0;
        } finally {
            returnToPool(jedis);
        }
    }


    /**
     * 将redis连接对象归还到redis连接池
     *
     * @param jedis
     */
    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
