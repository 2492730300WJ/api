package com.wczx.api.common.dto.request.auth;

import lombok.Data;
import lombok.ToString;

/**
 * @author tanghu
 * @Date: 2019/11/6 13:14
 */
@Data
@ToString
public class AuthRequest {
    private String token;

    private String uri;

    private String httpMethod;
}
