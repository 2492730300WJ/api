package com.wczx.api.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: dd
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserDataStat {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_data_stat", type = IdType.AUTO)
    private Long userDataStatId;

    private Long userId;

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
}
