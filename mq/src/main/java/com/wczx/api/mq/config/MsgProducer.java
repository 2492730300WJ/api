package com.wczx.api.mq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wj
 */
@Component
public class MsgProducer{


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RabbitTemplate rabbitTemplate;


    public void sendMsg(String content) {
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend("test.topic", "a", content);
    }
}
