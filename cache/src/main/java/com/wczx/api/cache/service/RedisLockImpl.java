package com.wczx.api.cache.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * Redis分布式锁
 *
 * @author wj
 */
@Service
public class RedisLockImpl implements DLockApi {

    /**
     * 通过连接池对象可以获得对redis的连接
     */
    @Resource
    private JedisPool jedisPool;

    private final String LOCK_SUCCESS = "OK";
    private final String SET_IF_NOT_EXIST = "NX";
    private final String SET_WITH_EXPIRE_TIME = "PX";

    private final Long RELEASE_SUCCESS = 1L;

    /**
     * 获取锁
     *
     * @param lockKey     锁
     * @param uniqueValue 能够唯一标识请求的值，以此保证锁的加解锁是同一个客户端
     * @param expireTime  过期时间, 单位：milliseconds
     * @return boolean
     */
    @Override
    public boolean lock(String lockKey, String uniqueValue, int expireTime) {

        //java1.7新特性:try-catch-resource（前提是resource必须继承自java.lang.AutoCloseable -- jedisPool）
        // 无需再手动关闭连接(实际编译过后仍有finally...close,只不过是隐式关闭了资源)
        try (Jedis jedis = jedisPool.getResource()) {
            // 获取锁  tips: public String set(String key, String value, String nxxx, String expx, int time):  nxxx(nx | xx)->只有key（不存在 | 存在）时才将value存到redis  :  expx(ex | px)->过期时间设置(秒 | 毫秒)
            String result = jedis.set(lockKey, uniqueValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            return LOCK_SUCCESS.equals(result);
        }
    }

    /**
     * 使用Lua脚本保证解锁的原子性
     *
     * @param lockKey     锁
     * @param uniqueValue 能够唯一标识请求的值，以此保证锁的加解锁是同一个客户端
     * @return boolean
     * @see <a href="http://www.lua.org/"></a>
     * @see <a href="https://www.cnblogs.com/huangxincheng/p/6230129.html"></a>
     */
    @Override
    public boolean unlock(String lockKey, String uniqueValue) {
        try (Jedis jedis = jedisPool.getResource()) {
            // 使用Lua脚本保证操作的原子性
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) " +
                    "else return 0 " +
                    "end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(uniqueValue));
            return RELEASE_SUCCESS.equals(result);
        }
    }
}
