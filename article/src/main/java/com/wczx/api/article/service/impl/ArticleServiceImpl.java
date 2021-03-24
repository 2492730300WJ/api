package com.wczx.api.article.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wczx.api.article.service.ArticleService;
import com.wczx.api.common.constant.ArticleConstant;
import com.wczx.api.common.constant.CacheConstant;
import com.wczx.api.common.dto.request.article.ArticleCommonRequestDTO;
import com.wczx.api.common.dto.request.cache.CacheCommonRequestDTO;
import com.wczx.api.common.dto.request.cache.LockCommonRequestDTO;
import com.wczx.api.common.response.WorkException;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.common.util.UUIDUtil;
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
     * "updateTime":"2021-01-01 12:12:12",
     * "author":1
     * }
     *
     * @param requestDTO
     * @return
     */
    @Override
    public Object getArticle(ArticleCommonRequestDTO requestDTO) throws InterruptedException {
        if (null == requestDTO.getArticleId()) {
            throw new WorkException(WorkStatus.CHECK_PARAM);
        }
        Object result = articleInfo(requestDTO);
        if (null == result){
            throw new WorkException(WorkStatus.ARTICLE_NOT_FOUND);
        }
        JSONObject article = JSONObject.parseObject(result.toString());
        article.put("watch", article.getIntValue("watch") + 1);
        CacheCommonRequestDTO commonRequestDTO = new CacheCommonRequestDTO();
        commonRequestDTO.setKey(CacheConstant.ARTICLE_PREFIX + requestDTO.getArticleId().toString());
        commonRequestDTO.setExpireSeconds(ArticleConstant.HOT_ARTICLE_TIME);
        commonRequestDTO.setValue(article.toString());
        cacheClient.set(commonRequestDTO);
        return article;
    }

    /**
     * 通用的article方法
     */
    public Object articleInfo(ArticleCommonRequestDTO requestDTO) throws InterruptedException {
        // 防止缓存穿透 使用bitmap校验 1 为 无效key
        CacheCommonRequestDTO penetration = new CacheCommonRequestDTO();
        penetration.setKey(CacheConstant.ARTICLE_CACHE_PENETRATION_KEY);
        penetration.setOffset(Integer.parseInt(requestDTO.getArticleId().toString()));
        WorkResponse penetrationFlag = cacheClient.getBit(penetration);
        if (!WorkStatus.SUCCESS.getWorkCode().equals(penetrationFlag.getCode())) {
            throw new WorkException(WorkStatus.FAIL);
        }
        // 为true 则为无效key
        if (Boolean.valueOf(penetrationFlag.getData().toString())) {
            return null;
        }

        // 请求redis
        CacheCommonRequestDTO commonRequestDTO = new CacheCommonRequestDTO();
        commonRequestDTO.setKey(CacheConstant.ARTICLE_PREFIX + requestDTO.getArticleId().toString());
        WorkResponse cache = cacheClient.get(commonRequestDTO);
        if (!WorkStatus.SUCCESS.getWorkCode().equals(cache.getCode())) {
            throw new WorkException(WorkStatus.FAIL);
        }
        // redis里没有，去查数据库（此处需防止缓存击穿）
        if (null == cache.getData()) {
            // 开启redis锁防止缓存击穿
            LockCommonRequestDTO lock = new LockCommonRequestDTO();
            lock.setLockKey(CacheConstant.ARTICLE_LOCK_KEY_PREFIX + requestDTO.getArticleId());
            lock.setExpireTime(1000);
            lock.setUniqueValue(UUIDUtil.getRandomStr(10));
            WorkResponse lockFlag = cacheClient.lock(lock);
            // 为true 则为无效key
            if (Boolean.valueOf(lockFlag.getData().toString())) {
                // 查数据库
                // 数据库有存入redis
                // 数据库也没有,防止缓存穿透 使用bitmap存储
                penetration.setValue("1");
                cacheClient.setBit(penetration);
                // 不需要finally
                cacheClient.unlock(lock);
                return null;
            } else {
                // 锁住了 休眠1s
                Thread.sleep(1000);
                return articleInfo(requestDTO);
            }
        }
        return cache.getData().toString();
    }

    @Override
    public Object starOrNoStarArticle(ArticleCommonRequestDTO requestDTO) throws InterruptedException {
        if (null == requestDTO.getArticleId() || null == requestDTO.getUserId()) {
            throw new WorkException(WorkStatus.CHECK_PARAM);
        }
        // 获取article信息
        Object result = articleInfo(requestDTO);
        if (null == result){
            throw new WorkException(WorkStatus.ARTICLE_NOT_FOUND);
        }
        // 查询是否点赞过 bitmap
        CacheCommonRequestDTO penetration = new CacheCommonRequestDTO();
        penetration.setKey(CacheConstant.ARTICLE_STAR_PREFIX + requestDTO.getArticleId());
        penetration.setOffset(Integer.parseInt(requestDTO.getUserId().toString()));
        WorkResponse starFlag = cacheClient.getBit(penetration);
        if (!WorkStatus.SUCCESS.getWorkCode().equals(starFlag.getCode())) {
            throw new WorkException(WorkStatus.FAIL);
        }
        // star = 1 且 点赞过了
        if (requestDTO.getStar() == 1 && Boolean.valueOf(starFlag.getData().toString())) {
            throw new WorkException(WorkStatus.ARTICLE_STAR_EXIST);
        }
        // star = 0 且 未点赞
        if (requestDTO.getStar() == 0 && !Boolean.valueOf(starFlag.getData().toString())) {
            return null;
        }
        // 1.存点赞的redis bitMap 增加or修改值
        penetration.setValue(requestDTO.getStar().toString());
        WorkResponse setFlag = cacheClient.setBit(penetration);
        if (!WorkStatus.SUCCESS.getWorkCode().equals(setFlag.getCode())) {
            throw new WorkException(WorkStatus.FAIL);
        }
        // 2.拿出点赞数，更新redis中的文章详情
        WorkResponse countFlag = cacheClient.bitCount(penetration);
        if (!WorkStatus.SUCCESS.getWorkCode().equals(countFlag.getCode())) {
            throw new WorkException(WorkStatus.FAIL);
        }
        JSONObject article = JSONObject.parseObject(result.toString());
        article.put("star", Integer.valueOf(countFlag.getData().toString()));
        CacheCommonRequestDTO commonRequestDTO = new CacheCommonRequestDTO();
        commonRequestDTO.setKey(CacheConstant.ARTICLE_PREFIX + requestDTO.getArticleId().toString());
        commonRequestDTO.setExpireSeconds(ArticleConstant.HOT_ARTICLE_TIME);
        commonRequestDTO.setValue(article.toString());
        cacheClient.set(commonRequestDTO);
        // 3.拿出作者id，给作者发通知(取消不发)
        System.out.println(article.getString("author"));
        return null;
    }
}
