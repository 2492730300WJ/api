package com.wczx.api.user.controller;

import com.wczx.api.common.dto.request.user.UserRequestDTO;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public WorkResponse login(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, userService.login(userRequestDTO));
    }

    /**
     * 用户信息
     */
    @PostMapping("/info")
    public WorkResponse info(@RequestBody UserRequestDTO userRequestDTO) {
       return new WorkResponse(WorkStatus.SUCCESS, userService.info(userRequestDTO));
    }

    /**
     * 用户信息(内部服务调用)
     */
    @PostMapping("/info-in")
    public WorkResponse infoIn(Long fromUser) {
        return new WorkResponse(WorkStatus.SUCCESS, userService.userIn(fromUser));
    }

}
