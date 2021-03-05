package com.wczx.api.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wj
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户账号
     */
    private String userCard;

    /**
     * 用户手机
     */
    private Long phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态（0.正常1.封禁2.注销）
     */
    private Integer status;

    private String createTime;

    private String updateTime;

}
