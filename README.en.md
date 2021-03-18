# Introduction
This project provides a series of APIs for the app to use as the background interface of [Confetti](https://github.com/2492730300WJ/Confetti). The project mainly uses spring cloud Alibaba and its related components to form the background core architecture.
<p align="center">
  <a>
    <img src="https://img.shields.io/badge/JDK-1.8-red">
  </a>
  <a>
    <img src="https://img.shields.io/badge/SpringCloud-Hoxton.SR8-green">
  </a>
  <a>
     <img src="https://img.shields.io/badge/SpringCloud%20Alibaba-2.2.5.RELEASE-brightgreen">
  </a>
  <a>
     <img src="https://img.shields.io/badge/SpringBoot-2.3.2.RELEASE-yellow">
  </a>
  <a>
     <img src="https://img.shields.io/badge/Mybatis--Plus-3.4.2-blue">
  </a>
  <a>
      <img src="https://img.shields.io/badge/Nacos-1.4.1-green">
  </a>
</p>

[简体中文](./README.md) | English

---
# Basic structure
## api
 -  [gateway --Unified gateway](https://github.com/2492730300WJ/api/tree/master/gateway/README.md)
 -  user     --User services
 -  [ws --Chat service](https://github.com/2492730300WJ/api/blob/master/ws/README.md)
 -  [article --Article service](https://github.com/2492730300WJ/api/tree/master/article/README.md)
 -  cache    --Caching service
 -  mq       --Message service
 -  auth     --Authentication service
 -  schedule --Time service
## Common package
 -         feign    FeignClient  
 -         commons  dto ，constant
 ---
# Technology stack
    -Java
        --SpringCloud
        --SpringBoot
        --SpringCloud Alibaba
        --SpringCloud Bus
            Message bus
        --SpringCloud Stream
            Message driven (better to use rabbitmq directly)  
        --SpringCloud Gateway
            Gateway Services
        --SpringCloud Sentinel
            Sentinel, flow control, fuse degradation, system load protection
        --SpringCloud Seata
            Distributed transaction
        --SpringCloud Sleuth && Zipkin
            Link monitoring
        --Nacos
            Service registration, configuration
        --OpenFeign
            Call between services
        --Mybatis-Plus
        --WebSocket
        --JWT
        --redis
    -Server
        --Docker
        --Jenkins
# Required components
##   Nacos
     	Go to the official website to download the Nacos server
        Default port:8848
        Persistence notes:
            1. Create database and table SQL location: installation directory/nacos/conf/nacos-mysql.sql
            2.Add configuration directory: installation directory/nacos/conf/application.properties
               spring.datasource.platform=mysql
               db.num=1
               db.url.0=
               db.user=
               db.password= 
        Notice for startup:
            Double click the installation directory/nacos/bin/startup.cmd
            Default cluster
            Single machine modifiable configuration(windows)：
                Modify Directory: install directory/nacos/bin/startup.cmd
                    set MODE="cluster"
                    Change to:
                    rem set MODE="cluster"
                    set MODE="standalone"
## Sentinal
        Download to official website: sentinel-dashboard-1.8.1.jar
        Default port: 8080
        Start up: java -jar sentinel-dashboard-1.8.1.jar
        Persist Sentinal configuration (YML) in NOCOS:
            sentinel:
                  # Cancel console lazy loading
                  eager: true
                  transport:
                    dashboard: ${SENTINEL_DASHBOARD_ADDR}
                    port:
                      8719
                  datasource:
                    ds1:
                      nacos:
                        server-addr: ${NACOS_SERVER_ADDR}
                        dataId: xxx
                        groupId: xxx
                        data-type: json
                        rule-type: flow
##  SpringCloud Sleuth && Zipkin
        Download to official website: zipkin-server-2.12.9-exec.jar
        Default port: 9411
        Start up: java -jar zipkin-server-2.12.9-exec.jar
# link
![Link diagram](http://47.102.121.70/file/zipkin.png)
