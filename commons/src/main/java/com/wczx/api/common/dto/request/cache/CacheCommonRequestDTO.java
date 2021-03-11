package com.wczx.api.common.dto.request.cache;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: wj
 */
@Data
public class CacheCommonRequestDTO {
    private String prefix;
    private String key;
    private Integer expireSeconds;
    private String value;
    private List<String> listValue;
}
