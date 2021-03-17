package com.wczx.api.user.controller;

import com.wczx.api.common.constant.AuthConstant;
import com.wczx.api.common.constant.GatewayConstant;
import com.wczx.api.common.dto.request.user.UserRequestDTO;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public WorkResponse login(HttpServletRequest request, @RequestBody UserRequestDTO userRequestDTO) {
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
    public WorkResponse infoIn(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, userService.userIn(userRequestDTO.getUserId()));
    }

}
