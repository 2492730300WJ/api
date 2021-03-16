package com.wczx.api.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: wj
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ScheduleApplication {
    public static void main(String[] args) {
            SpringApplication.run(ScheduleApplication.class, args);
    }
}
