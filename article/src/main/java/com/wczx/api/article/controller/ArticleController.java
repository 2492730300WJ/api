package com.wczx.api.article.controller;

import com.wczx.api.article.service.ArticleService;
import com.wczx.api.common.dto.request.cache.CacheCommonRequestDTO;
import com.wczx.api.common.dto.request.user.UserRequestDTO;
import com.wczx.api.common.response.WorkException;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.feign.client.CacheClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: wj
 */
@RestController
@RequestMapping("article")
public class ArticleController {

    /*
    Redis Value
    {
        "title": "文章标题",
        "content": "文章内容",
        "stat": 1,
        "fork": 1，
        "watch":1,
        "articleId":1,
        "createTime":"2021-01-01 11:11:11",
        "updateTime":"2021-01-01 12:12:12"
    }
     */

    @Resource
    ArticleService articleService;

    @Resource
    CacheClient cacheClient;

    /**
     * 获取文章详情
     * 1.查询缓存，增加缓存阅读列表 判断同一用户看同一篇文章一天只记录一次,有缓存返回，缓存内阅读数+1，
     * 2.无缓存，查询数据库，存入缓存
     */
    @PostMapping("/get")
    public WorkResponse getArticle(@RequestBody UserRequestDTO userRequestDTO) {
        CacheCommonRequestDTO commonRequestDTO = new CacheCommonRequestDTO();
        commonRequestDTO.setPrefix("article_");
        commonRequestDTO.setKey("1");
        WorkResponse cache = cacheClient.get(commonRequestDTO);
        if (!WorkStatus.SUCCESS.getWorkCode().equals(cache.getCode())){
            throw new WorkException(WorkStatus.FAIL);
        }
        commonRequestDTO.setValue("1");
        if(null != cache.getData()){
            commonRequestDTO.setValue((Integer.valueOf(cache.getData().toString()) + 1 ) + "");
        }
        cacheClient.set(commonRequestDTO);
        return new WorkResponse(WorkStatus.SUCCESS, null);
    }

    /**
     * 保存文章
     * 1.存入数据库
     * 2.存入缓存
     * 3.通过消息服务发送消息给好友附带跳转链接
     */
    @PostMapping("/save")
    public WorkResponse saveArticle(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, null);
    }

    /**
     * 修改文章
     * 1.存入数据库
     * 2.存入缓存or修改缓存
     * 3.通过定时任务每小时存入一次MYSQL数据库主要修改浏览数
     */
    @PostMapping("/update")
    public WorkResponse updateArticle(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, null);
    }

    /**
     * 点赞文章
     * 1.存入数据库
     * 2.存入缓存or修改缓存
     * 3.通过消息服务发送消息给作者
     * 大概格式 ：xx 在 time 点赞了 您的 xxx文章
     */
    @PostMapping("/star")
    public WorkResponse starArticle(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, null);
    }

    /**
     * 取消点赞文章
     * 1.存入数据库
     * 2.存入缓存or修改缓存
     */
    @PostMapping("/no-star")
    public WorkResponse noStarArticle(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, null);
    }

    /**
     * 分享文章
     * 1.存入数据库
     * 2.存入缓存or修改缓存
     * 3.通过消息服务发送消息给需要分享好友附带跳转链接
     */
    @PostMapping("/fork")
    public WorkResponse forkArticle(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, null);
    }

    /**
     * 评论文章
     * 1.存入数据库
     * 2.通过消息服务发送消息给作者附带跳转链接
     * 大概格式 ：xx 在 time 评论了 您的 xxx文章 评论内容 ：xxx
     */
    @PostMapping("/comment")
    public WorkResponse commentArticle(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, null);
    }

    /**
     * 回复
     * 1.存入数据库
     * 2.通过消息服务发送消息给作者附带跳转链接
     * 大概格式 ：xx 在 time 回复了 您在 xxx文章下的消息 消息内容 ：xxx 回复内容：xxx
     */
    @PostMapping("/reply")
    public WorkResponse replyComment(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, null);
    }
}
