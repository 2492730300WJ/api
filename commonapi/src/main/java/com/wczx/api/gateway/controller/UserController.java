package com.wczx.api.gateway.controller;

import com.wczx.api.dto.request.user.UserRequestDTO;
import com.wczx.api.gateway.config.jwt.UserLoginToken;
import com.wczx.api.gateway.config.response.CommonReturnController;
import com.wczx.api.gateway.config.response.WorkResponse;
import com.wczx.api.gateway.config.response.WorkStatus;
import com.wczx.api.gateway.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("api/user")
public class UserController extends CommonReturnController {

    @Autowired
    UserFeign userFeign;

    /**
     * 登录
     */
    @PostMapping("/login")
    public void login(HttpServletResponse response, @RequestBody UserRequestDTO userRequestDTO) {
        this.commonResponse(response, new WorkResponse(WorkStatus.SUCCESS, userFeign.login(userRequestDTO)));
    }

    /**
     * 用户信息
     */
    @PostMapping("/info")
    @UserLoginToken
    public void info(HttpServletResponse response, @RequestBody UserRequestDTO userRequestDTO) {
        this.commonResponse(response, new WorkResponse(WorkStatus.SUCCESS, userFeign.info(userRequestDTO)));
    }

}
