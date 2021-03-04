package com.wczx.api.article.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: wj
 */
@FeignClient(value = "api-websocket")
public interface WsFeignClient {

    @GetMapping("/ws/test")
    String test();
}
