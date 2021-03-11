package com.wczx.api.auth.controller;

import com.wczx.api.auth.service.AuthService;
import com.wczx.api.auth.service.TokenService;
import com.wczx.api.common.dto.request.auth.AuthRequest;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.common.session.SessionInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wj
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @Resource
    private TokenService tokenService;

    @PostMapping("auth")
    public WorkResponse auth(@RequestBody AuthRequest request){
        return new WorkResponse(WorkStatus.SUCCESS,authService.auth(request));
    }

    @PostMapping("token")
    public WorkResponse token(@RequestBody SessionInfo request){
        return new WorkResponse(WorkStatus.SUCCESS,tokenService.token(request));
    }

    @PostMapping("refresh-token")
    public WorkResponse refreshToken(@RequestBody SessionInfo request){
        return new WorkResponse(WorkStatus.SUCCESS,tokenService.refreshToken(request));
    }

}
