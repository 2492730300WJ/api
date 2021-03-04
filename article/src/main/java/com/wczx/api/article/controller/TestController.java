package com.wczx.api.article.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.wczx.api.article.feign.WsFeignClient;
import com.wczx.api.common.blockhandler.BlockHandler;
import com.wczx.api.common.response.CommonReturnController;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


/**
 * @author Administrator
 */
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
