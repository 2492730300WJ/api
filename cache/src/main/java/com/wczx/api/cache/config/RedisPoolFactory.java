package com.wczx.api.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * jedis连接池
 * @author wj
 */
@Component
public class RedisPoolFactory {

    @Resource
    RedisConfig redisConfig;

    @Bean
    public JedisPool JedisPoolFactory() {
        // jedis连接池
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);

        JedisPool pool = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout(),redisConfig.getPassword());
        return pool;
    }
}
