package com.wczx.api.common.dto.request.cache;

import lombok.Data;

/**
 * @author: wj
 */
@Data
public class LockCommonRequestDTO {
    private String lockKey;
    private String uniqueValue;
    private Integer expireTime;
}
