package com.wczx.api.ws.entity;

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
public class PrivateMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long fromUser;

    private Long toUser;

    private String message;

    private Integer type;

    private String time;

    private String isRead;

    private String isDelete;

    private String isOld;
}
