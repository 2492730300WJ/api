package com.wczx.api.common.dto.request.article;

import lombok.Data;

/**
 * @author: wj
 */
@Data
public class ArticleCommonRequestDTO {
    private Long articleId;
    private Long userId;
    private Integer star;
}
