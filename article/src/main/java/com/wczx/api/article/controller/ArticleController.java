package com.wczx.api.article.controller;

import com.alibaba.fastjson.JSONObject;
import com.wczx.api.article.service.ArticleService;
import com.wczx.api.common.dto.request.article.ArticleCommonRequestDTO;
import com.wczx.api.common.dto.request.user.UserRequestDTO;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.common.session.SessionInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: wj
 */
@RestController
@RequestMapping("article")
public class ArticleController {

    @Resource
    ArticleService articleService;


    /**
     * 获取文章列表
     */
    @PostMapping("/list")
    public WorkResponse getArticleList(@RequestBody UserRequestDTO userRequestDTO) {
        return new WorkResponse(WorkStatus.SUCCESS, null);
    }


    /**
     * 获取文章详情
     * 1.查询缓存，增加缓存阅读列表 有缓存返回，缓存内阅读数+1，
     * 2.无缓存，查询数据库，存入缓存
     */
    @PostMapping("/get")
    public WorkResponse getArticle(@RequestBody ArticleCommonRequestDTO requestDTO) throws InterruptedException {
        return new WorkResponse(WorkStatus.SUCCESS, articleService.getArticle(requestDTO));
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
    public WorkResponse starArticle(@RequestHeader("sessionInfo") String sessionInfo, @RequestBody ArticleCommonRequestDTO requestDTO) throws InterruptedException {
        SessionInfo info = JSONObject.parseObject(sessionInfo, SessionInfo.class);
        requestDTO.setUserId(info.getUserId());
        requestDTO.setStar(1);
        return new WorkResponse(WorkStatus.SUCCESS, articleService.starOrNoStarArticle(requestDTO));
    }

    /**
     * 取消点赞文章
     * 1.存入数据库
     * 2.存入缓存or修改缓存
     */
    @PostMapping("/no-star")
    public WorkResponse noStarArticle(@RequestHeader("sessionInfo") String sessionInfo, @RequestBody ArticleCommonRequestDTO requestDTO) throws InterruptedException {
        SessionInfo info = JSONObject.parseObject(sessionInfo, SessionInfo.class);
        requestDTO.setUserId(info.getUserId());
        requestDTO.setStar(0);
        return new WorkResponse(WorkStatus.SUCCESS, articleService.starOrNoStarArticle(requestDTO));
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
