package com.wczx.api.schedule.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: wj
 * 每隔6小时同步一次article数据
 * 注：redis缓存article的数据最低可以保存12小时，且每次修改/浏览/点赞/分享都会刷新redis缓存过期时间
 */
@Component
@EnableScheduling
public class SyncArticleTask {
    @Scheduled(cron = "0 * * * * ? ")
    public void syncArticleTask() {
        System.out.println("开始同步Article");
        // TODO 记录定时任务开始时间，保存至数据库
        /*
            TODO 业务逻辑
            1、查询article数据库
            2、循环article列表
            3、拼接redis KEY 查询 redis
            4、将redis的数据同步至article
         */
        // TODO 记录定时任务时间以及定时任务状态，保存至数据库
    }
}
