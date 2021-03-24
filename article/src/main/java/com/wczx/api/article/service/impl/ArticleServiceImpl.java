package com.wczx.api.article.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wczx.api.article.service.ArticleService;
import com.wczx.api.common.constant.ArticleConstant;
import com.wczx.api.common.constant.CacheConstant;
import com.wczx.api.common.dto.request.article.ArticleCommonRequestDTO;
import com.wczx.api.common.dto.request.cache.CacheCommonRequestDTO;
import com.wczx.api.common.response.WorkException;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.feign.client.CacheClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: wj
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    CacheClient cacheClient;


    /**
     * Redis Value
     * {
     * "title": "文章标题",
     * "content": "文章内容",
     * "star": 1,
     * "fork": 1，
     * "watch":1,
     * "articleId":1,
     * "createTime":"2021-01-01 11:11:11",
     * "updateTime":"2021-01-01 12:12:12"
     * }
     *
     * @param requestDTO
     * @return
     */
    @Override
    public Object getArticle(ArticleCommonRequestDTO requestDTO) {
        if (null == requestDTO.getArticleId()) {
            throw new WorkException(WorkStatus.CHECK_PARAM);
        }
        CacheCommonRequestDTO commonRequestDTO = new CacheCommonRequestDTO();
        commonRequestDTO.setPrefix("article_");
        commonRequestDTO.setKey(requestDTO.getArticleId().toString());
        WorkResponse cache = cacheClient.get(commonRequestDTO);
        if (!WorkStatus.SUCCESS.getWorkCode().equals(cache.getCode())) {
            throw new WorkException(WorkStatus.FAIL);
        }

        if (null == cache.getData()) {
            // 查数据库
            // 数据库有存入redis
            // 数据库也没有,防止缓存穿透
            commonRequestDTO.setValue(CacheConstant.CACHE_PENETRATION_MSG);
            // 30S有效期
            commonRequestDTO.setExpireSeconds(CacheConstant.CACHE_PENETRATION_TIME);
            cacheClient.set(commonRequestDTO);
            return null;
        }
        if (CacheConstant.CACHE_PENETRATION_MSG.equals(cache.getData().toString())){
            // 恶意刷信息，防止缓存穿透 直接返回null
            return null;
        }
        JSONObject article = JSONObject.parseObject(cache.getData().toString());
        article.put("watch", article.getIntValue("watch") + 1);
        commonRequestDTO.setExpireSeconds(ArticleConstant.HOT_ARTICLE_TIME);
        commonRequestDTO.setValue(article.toString());
        cacheClient.set(commonRequestDTO);
        return article;
    }
}
