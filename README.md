# 基本结构
    -api
        --gateway 统一网关
        --user    用户服务
        --commons 公共类
        --ws      webSocket服务
        --article 文章服务
        --cache   缓存服务
        --mq      消息服务
        --auth    鉴权服务
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
    -服务器
        --Docker
        --Jenkins
# 所需组件
    Nacos：
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
    Sentinal:
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
    SpringCloud Sleuth && Zipkin:
        前往官网下载zipkin-server-2.12.9-exec.jar
        默认端口9411
        启动：java -jar zipkin-server-2.12.9-exec.jar
