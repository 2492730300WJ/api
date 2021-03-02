package com.wczx.api.gateway.feign;

import com.alibaba.fastjson.JSONObject;
import com.wczx.api.dto.request.user.UserRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "api-user")
public interface UserFeign {

    @PostMapping(value = "test")
    JSONObject login(UserRequestDTO userRequestDTO);

    @PostMapping(value = "info")
    JSONObject info(UserRequestDTO userRequestDTO);
}
