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
    public String get(String key) {
        // redis连接
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            // 通过key获取存储于redis中的对象（这个对象是以json格式存储的，所以是字符串）
            String strValue = jedis.get(key);
            // 将json字符串转换为对应的对象
            return strValue;
        } finally {
            // 归还redis连接到连接池
            returnToPool(jedis);
        }
    }

    @Override
    public boolean set(String key, int expireSeconds, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (value == null) {
                return false;
            }
            // 获取key的过期时间
            int expires = expireSeconds;

            if (expires <= 0) {
                // 设置key的过期时间为redis默认值（由redis的缓存策略控制）
                jedis.set(key, value);
            } else {
                // 设置key的过期时间
                jedis.setex(key, expires, value);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public boolean setList(String key, int expireSeconds, List<String> value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            if (value == null || value.size() <= 0) {
                return false;
            }

            // 获取key的过期时间
            int expires = expireSeconds;

            if (expires <= 0) {
                // 设置key的过期时间为redis默认值（由redis的缓存策略控制）
                jedis.rpush(key, value.toArray(new String[value.size()]));
            } else {
                // 设置key的过期时间
                jedis.rpush(key, value.toArray(new String[value.size()]));
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public long decr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.decr(key);
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public boolean delete(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long del = jedis.del(key);
            return del > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public boolean setBit(String key, String value, Integer offset) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (value == null) {
                return false;
            }
            jedis.setbit(key,offset,value);
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public boolean getBit(String key, Integer offset) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.getbit(key,offset);
        } finally {
            returnToPool(jedis);
        }
    }

    @Override
    public Long bitCount(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.bitcount(key);
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
