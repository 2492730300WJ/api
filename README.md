# 简介
此项目为[Confetti](https://github.com/2492730300WJ/Confetti)的后台接口，提供了一系列api以供app使用，项目主要用到了SpringCloud Alibaba及其相关组件构成后台核心架构。
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

简体中文 | [English](./README.en.md)

---
# 基本结构
## api
 -  [gateway 统一网关](https://github.com/2492730300WJ/api/tree/master/gateway/README.md)
 -  user     用户服务
 -  [ws 聊天服务](https://github.com/2492730300WJ/api/blob/master/ws/README.md)
 -  [article 文章服务](https://github.com/2492730300WJ/api/tree/master/article/README.md)
 -  cache    缓存服务
 -  mq       消息服务
 -  auth     鉴权服务
 -  schedule 定时服务
 -  monitor  监控服务
## 公共包
 -         feign    FeignClient  
 -         commons  dto ，constant
 ---
# 技术栈
    -Java
        --SpringCloud
        --SpringBoot
        --SpringCloud Alibaba
        --SpringCloud Bus
            消息总线
        --SpringCloud Stream
            消息驱动(直接使用rabbitMq更好)    
        --SpringCloud Gateway
            网关服务
        --SpringCloud Sentinel
            哨兵，流量控制、熔断降级、系统负载保护
        --SpringCloud Seata
            分布式事务
        --SpringCloud Sleuth && Zipkin
            链路监控
        --Nacos
            服务注册，配置
        --OpenFeign
            服务之间调用
        --Mybatis-Plus
        --WebSocket
        --JWT
        --redis
        --SpringBoot admin
    -服务器
        --Docker
        --Jenkins
# 所需组件
##   Nacos
     	前往官网下载nacos服务端
        默认端口8848
        持久化须知：
            1.创建数据库和表 sql位置：安装目录/nacos/conf/nacos-mysql.sql
            2.添加配置 配置目录：安装目录/nacos/conf/application.properties
               spring.datasource.platform=mysql
               db.num=1
               db.url.0=
               db.user=
               db.password= 
        启动须知：
            双击 安装目录/nacos/bin/startup.cmd
            默认集群
            单机可修改配置(windows)：
                修改目录：安装目录/nacos/bin/startup.cmd
                    set MODE="cluster"
                    改为：
                    rem set MODE="cluster"
                    set MODE="standalone"
## Sentinal
        前往官网下载sentinel-dashboard-1.8.1.jar
        默认端口8080
        启动：java -jar sentinel-dashboard-1.8.1.jar
        在nocos中持久化sentinal配置(yml):
            sentinel:
                  # 取消控制台懒加载
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
        前往官网下载zipkin-server-2.12.9-exec.jar
        默认端口9411
        启动：java -jar zipkin-server-2.12.9-exec.jar
# 链路
![链路图](http://47.102.121.70/file/zipkin.png)
