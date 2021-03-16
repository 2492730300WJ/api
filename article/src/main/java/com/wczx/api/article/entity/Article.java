package com.wczx.api.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: wj
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    /**
     * 作者id
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer star;

    /**
     * 分享数
     */
    private Integer fork;

    /**
     * 阅读数
     */
    private Integer watch;

    /**
     * 是否私有（好友才能看）
     */
    private String privateFlag;

    /**
     * 状态（0.正常1.封禁2.注销）
     */
    private Integer status;

    private String createTime;

    private String updateTime;

}
