package com.wczx.api.article.service;

import com.wczx.api.common.dto.request.article.ArticleCommonRequestDTO;

/**
 * @author Administrator
 */
public interface ArticleService {

    Object getArticle(ArticleCommonRequestDTO requestDTO) throws InterruptedException;
}
