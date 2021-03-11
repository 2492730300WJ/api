package com.wczx.api.auth.service;

import com.wczx.api.common.dto.request.auth.AuthRequest;

/**
 * @author: wj
 */
public interface AuthService {

    /**
     * 校验Token，权限
     * @param request
     * @return
     */
    Object auth(AuthRequest request);

}
