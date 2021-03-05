package com.wczx.api.ws;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wj
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"com.wczx.api.ws.mapper"})
public class WsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsApplication.class, args);
    }

}
