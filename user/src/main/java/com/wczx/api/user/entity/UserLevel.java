package com.wczx.api.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: dd
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_level_id", type = IdType.AUTO)
    private Long userLevelId;

    private Long userId;

    private Integer level;

    private Integer grow;
}
