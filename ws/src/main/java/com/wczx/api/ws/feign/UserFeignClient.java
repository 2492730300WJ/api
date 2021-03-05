package com.wczx.api.ws.feign;

import com.wczx.api.common.response.WorkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: wj
 */
@FeignClient(value = "api-user")
public interface UserFeignClient {

    @PostMapping("/info-in")
    WorkResponse infoIn(Long fromUser);
}
