package com.wczx.api.common.dto.response.user;

import lombok.Data;

/**
 * @Author: dd
 */
@Data
public class UserInfoResponseDTO {

    /**
     * 关注
     */
    private Long follow;
    /**
     * 粉丝
     */
    private Long fans;
    /**
     * 热度
     */
    private Long hot;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 成长值
     */
    private Integer grow;
    /**
     * 成长值进度百分比
     */
    private String progressWidth;
    /**
     * 昵称
     */
    private String userName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 描述
     */
    private String description;

    /**
     * 描述
     */
    private Long phone;

}
