package com.wczx.api.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.wczx.api.blockhandler.BlockHandler;
import com.wczx.api.response.CommonReturnController;
import com.wczx.api.response.WorkResponse;
import com.wczx.api.response.WorkStatus;
import com.wczx.api.user.feign.WsFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class TestController extends CommonReturnController {

    @Autowired
    WsFeignClient wsFeignClient;

    @GetMapping("user/test")
    @SentinelResource(value = "test", blockHandlerClass = BlockHandler.class, blockHandler = "blockerHandler")
    public void test(HttpServletResponse response) {
        System.out.println("接到访问请求");
        this.commonResponse(response, new WorkResponse(WorkStatus.SUCCESS, "haha" + wsFeignClient.test()));
    }

}
