package com.wczx.api.feign.client;

import com.wczx.api.common.dto.request.user.UserRequestDTO;
import com.wczx.api.common.response.WorkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: wj
 */
@FeignClient(value = "api-user")
public interface UserClient {

    @PostMapping("user/info-in")
    WorkResponse infoIn(@RequestBody UserRequestDTO userRequestDTO);
}
