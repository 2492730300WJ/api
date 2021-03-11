package com.wczx.api.auth.service;

import com.wczx.api.common.session.SessionInfo;

/**
 * @author: wj
 */
public interface TokenService {
    String token(SessionInfo sessionInfo);
    String refreshToken(SessionInfo sessionInfo);
}
