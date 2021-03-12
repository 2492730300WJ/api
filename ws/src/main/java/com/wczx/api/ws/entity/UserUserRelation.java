package com.wczx.api.ws.entity;

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
public class UserUserRelation {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long fromUser;

    private String remark;
    /**
     * 状态（0.正常1.拉黑2.删除3.特别关心）
     */
    private Integer status;
}
