package com.wczx.api.feign.client;

import com.wczx.api.common.dto.request.auth.AuthRequest;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.session.SessionInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: wj
 * auth 服务相关接口
 */
@FeignClient(value = "api-auth")
public interface AuthClient {

    /**
     * 校验token和权限
     * @param request
     * @return
     */
    @PostMapping("/auth/auth")
    WorkResponse auth(@RequestBody AuthRequest request);

    /**
     * 短token
     * @param request
     * @return
     */
    @PostMapping("/auth/token")
    WorkResponse token(@RequestBody SessionInfo request);

    /**
     * 长token
     * @param request
     * @return
     */
    @PostMapping("/auth/refresh-token")
    WorkResponse refreshToken(@RequestBody SessionInfo request);
}
