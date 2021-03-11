package com.wczx.api.user.controller;

import com.wczx.api.common.response.WorkResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wj
 */
@RestController
public class ErrorController {
    @GetMapping("auth-error")
    public WorkResponse error(Integer code,String message) {
        return new WorkResponse(code,message);
    }
}
