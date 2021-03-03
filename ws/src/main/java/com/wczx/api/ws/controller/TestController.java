package com.wczx.api.ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wj
 */
@RestController
public class TestController {
    @GetMapping("/ws/test")
    public String test(){
        int i = 1/0;
        System.out.println("神经");
        return "神经";
    }
}
