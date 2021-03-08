package com.wczx.api.cache.controller;

import com.wczx.api.cache.service.DLockApi;
import com.wczx.api.cache.service.KeyPrefix;
import com.wczx.api.cache.service.RedisServiceApi;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: wj
 */
@RestController
@RequestMapping("cache")
public class CacheController<T> {

    @Resource
    DLockApi dLockApi;

    @Resource
    RedisServiceApi redisServiceApi;

    /**
     * lock
     */
    @GetMapping("/lock")
    public WorkResponse lock(String lockKey, String uniqueValue, int expireTime) {
        return new WorkResponse(WorkStatus.SUCCESS, dLockApi.lock(lockKey, uniqueValue, expireTime));
    }

    /**
     * lock
     */
    @GetMapping("/unlock")
    public WorkResponse unlock(String lockKey, String uniqueValue) {
        return new WorkResponse(WorkStatus.SUCCESS, dLockApi.unlock(lockKey, uniqueValue));
    }

    /**
     * get
     */
    @GetMapping("/get")
    public WorkResponse get(KeyPrefix prefix, String key, Class<T> clazz) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.get(prefix, key, clazz));
    }

    /**
     * set
     */
    @GetMapping("/set")
    public WorkResponse set(KeyPrefix prefix, String key, T value) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.set(prefix, key, value));
    }

    /**
     * setList
     */
    @GetMapping("/set-list")
    public WorkResponse setList(KeyPrefix prefix, String key, List<String> value) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.setList(prefix, key, value));
    }

    /**
     * exists
     */
    @GetMapping("/exists")
    public WorkResponse exists(KeyPrefix keyPrefix, String key) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.exists(keyPrefix, key));
    }

    /**
     * incr
     */
    @GetMapping("/incr")
    public WorkResponse incr(KeyPrefix keyPrefix, String key) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.incr(keyPrefix, key));
    }

    /**
     * decr
     */
    @GetMapping("/decr")
    public WorkResponse decr(KeyPrefix keyPrefix, String key) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.decr(keyPrefix, key));
    }

    /**
     * delete
     */
    @GetMapping("/delete")
    public WorkResponse delete(KeyPrefix keyPrefix, String key) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.delete(keyPrefix, key));
    }


}
