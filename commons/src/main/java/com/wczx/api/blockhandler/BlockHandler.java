package com.wczx.api.blockhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author: wj
 */
public class BlockHandler {
    public static String blockHandler(BlockException b) {
        return "限流啦~~~";
    }
}
