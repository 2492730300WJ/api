package com.wczx.api.feign.client;

import com.wczx.api.common.dto.request.cache.CacheCommonRequestDTO;
import com.wczx.api.common.dto.request.cache.LockCommonRequestDTO;
import com.wczx.api.common.response.WorkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: wj
 * cache 服务相关接口
 */
@FeignClient(value = "api-cache")
public interface CacheClient {
    /**
     * lock
     */
    @PostMapping("/cache/lock")
    WorkResponse lock(@RequestBody LockCommonRequestDTO requestDTO);

    /**
     * unlock
     */
    @PostMapping("/cache/unlock")
    WorkResponse unlock(@RequestBody LockCommonRequestDTO requestDTO);

    /**
     * get
     */
    @PostMapping("/cache/get")
    WorkResponse get(@RequestBody CacheCommonRequestDTO requestDTO);

    /**
     * set
     */
    @PostMapping("/cache/set")
    WorkResponse set(@RequestBody CacheCommonRequestDTO requestDTO);

    /**
     * setList
     */
    @PostMapping("/cache/set-list")
    WorkResponse setList(@RequestBody CacheCommonRequestDTO requestDTO);

    /**
     * exists
     */
    @PostMapping("/cache/exists")
    WorkResponse exists(@RequestBody CacheCommonRequestDTO requestDTO);

    /**
     * incr
     */
    @PostMapping("/cache/incr")
    WorkResponse incr(@RequestBody CacheCommonRequestDTO requestDTO);

    /**
     * decr
     */
    @PostMapping("/cache/decr")
    WorkResponse decr(@RequestBody CacheCommonRequestDTO requestDTO);

    /**
     * delete
     */
    @PostMapping("/cache/delete")
    WorkResponse delete(@RequestBody CacheCommonRequestDTO requestDTO);

    /**
     * setBit
     */
    @PostMapping("/cache/set-bit")
    WorkResponse setBit(@RequestBody CacheCommonRequestDTO requestDTO);

    /**
     * getBit
     */
    @PostMapping("/cache/get-bit")
    WorkResponse getBit(@RequestBody CacheCommonRequestDTO requestDTO);

    /**
     * bit count
     */
    @PostMapping("/cache/bit-count")
    WorkResponse bitCount(@RequestBody CacheCommonRequestDTO requestDTO);
}
