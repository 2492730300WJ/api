package com.wczx.api.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("user/test")
    public String test(){
        System.out.println("接到访问请求");
        return "1号User";
    }

}
