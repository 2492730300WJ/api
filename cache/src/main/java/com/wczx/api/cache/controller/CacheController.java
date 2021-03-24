package com.wczx.api.cache.controller;

import com.wczx.api.cache.service.DLockApi;
import com.wczx.api.cache.service.RedisServiceApi;
import com.wczx.api.common.dto.request.cache.CacheCommonRequestDTO;
import com.wczx.api.common.dto.request.cache.LockCommonRequestDTO;
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
    @PostMapping("/lock")
    public WorkResponse lock(@RequestBody LockCommonRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, dLockApi.lock(requestDTO.getLockKey(), requestDTO.getUniqueValue(), requestDTO.getExpireTime()));
    }

    /**
     * lock
     */
    @PostMapping("/unlock")
    public WorkResponse unlock(@RequestBody LockCommonRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, dLockApi.unlock(requestDTO.getLockKey(), requestDTO.getUniqueValue()));
    }

    /**
     * get
     */
    @PostMapping("/get")
    public WorkResponse get(@RequestBody CacheCommonRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.get(requestDTO.getKey()));
    }

    /**
     * set
     */
    @PostMapping("/set")
    public WorkResponse set(@RequestBody CacheCommonRequestDTO requestDTO) {
        if (null == requestDTO.getExpireSeconds()) {
            requestDTO.setExpireSeconds(0);
        }
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.set(requestDTO.getKey(), requestDTO.getExpireSeconds(), requestDTO.getValue()));
    }

    /**
     * setList
     */
    @PostMapping("/set-list")
    public WorkResponse setList(@RequestBody CacheCommonRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.setList(requestDTO.getKey(), requestDTO.getExpireSeconds(), requestDTO.getListValue()));
    }

    /**
     * exists
     */
    @PostMapping("/exists")
    public WorkResponse exists(@RequestBody CacheCommonRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.exists(requestDTO.getKey()));
    }

    /**
     * incr
     */
    @PostMapping("/incr")
    public WorkResponse incr(@RequestBody CacheCommonRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.incr(requestDTO.getKey()));
    }

    /**
     * decr
     */
    @PostMapping("/decr")
    public WorkResponse decr(@RequestBody CacheCommonRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.decr(requestDTO.getKey()));
    }

    /**
     * delete
     */
    @PostMapping("/delete")
    public WorkResponse delete(@RequestBody CacheCommonRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.delete(requestDTO.getKey()));
    }


    /**
     * set bit
     */
    @PostMapping("/set-bit")
    public WorkResponse setBit(@RequestBody CacheCommonRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.setBit(requestDTO.getKey(), requestDTO.getValue(), requestDTO.getOffset()));
    }

    /**
     * get bit
     */
    @PostMapping("/get-bit")
    public WorkResponse getBit(@RequestBody CacheCommonRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.getBit(requestDTO.getKey(), requestDTO.getOffset()));
    }

    /**
     * get bit
     */
    @PostMapping("/bit-count")
    public WorkResponse bitCount(@RequestBody CacheCommonRequestDTO requestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, redisServiceApi.bitCount(requestDTO.getKey()));
    }
}
