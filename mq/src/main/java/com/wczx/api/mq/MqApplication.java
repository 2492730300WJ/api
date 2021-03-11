package com.wczx.api.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: wj
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MqApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqApplication.class,args);
    }
}
