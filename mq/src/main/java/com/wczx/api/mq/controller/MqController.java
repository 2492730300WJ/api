package com.wczx.api.mq.controller;

import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.mq.config.MsgProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: wj
 */
@RestController
@RequestMapping("mq")
public class MqController {

    @Resource
    MsgProducer msgProducer;

    @GetMapping("sendMessage")
    public WorkResponse send(){
        msgProducer.sendMsg("hahah");
        return new WorkResponse(WorkStatus.SUCCESS);
    }
}
