package com.wczx.api.common.constant;

/**
 * @author: wj
 */
public class CacheConstant {
    /**
     * 防止缓存穿透时间
     */
    public static final Integer CACHE_PENETRATION_TIME = 30;

    /**
     * 缓存穿透
     */
    public static final String CACHE_PENETRATION_MSG = "Cache penetration";

}
