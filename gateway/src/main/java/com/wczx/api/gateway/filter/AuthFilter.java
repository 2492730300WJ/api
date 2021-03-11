package com.wczx.api.gateway.filter;

import com.wczx.api.common.constant.GatewayConstant;
import com.wczx.api.common.dto.request.auth.AuthRequest;
import com.wczx.api.common.response.WorkException;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.feign.client.AuthClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 权限校验
 *
 * @author wj
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Resource
    private AuthClient authClient;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        MDC.put("operatingId", String.valueOf(UUID.randomUUID()));
        log.info("MDC ADD");
        String url = exchange.getRequest().getURI().getPath();
        ServerHttpRequest request = exchange.getRequest();
        try {
            log.info(" access url :  " + url);
            String scheme = exchange.getRequest().getURI().getScheme();
            String host = exchange.getRequest().getURI().getHost();
            int port = exchange.getRequest().getURI().getPort();
            String basePath = scheme + "://" + host + ":" + port;
            log.info(" base path :  " + basePath);

            List<String> whiteUrl = new ArrayList<>();
            whiteUrl.add("/user/login");
            //跳过白名单
            if (null != whiteUrl && whiteUrl.contains(url)) {
                return chain.filter(exchange);
            }

            // 获取权限校验部分
            String authHeader = exchange.getRequest().getHeaders().getFirst(GatewayConstant.AUTH_HEADER);
            if (StringUtils.isBlank(authHeader)) {
                throw new WorkException(WorkStatus.CHECK_PARAM);
            }
            AuthRequest authRequest = new AuthRequest();
            authRequest.setToken(authHeader);
            authRequest.setHttpMethod(request.getMethodValue());
            authRequest.setUri(url);
            WorkResponse workResponse = authClient.auth(authRequest);
            if (!workResponse.getCode().equals(WorkStatus.SUCCESS.getWorkCode())) {
                throw new WorkException(workResponse.getCode(), workResponse.getMsg());
            }
            log.info("MDC END");
            return chain.filter(exchange);
        } catch (WorkException w) {
            ServerHttpRequest newRequest = request.mutate()
                    .path("/auth-error?code=" + w.getExceptionCode() + "&message=" + w.getExceptionMsg())
                    .method(HttpMethod.GET)
                    .build();
            log.info("MDC END");
            return chain.filter(exchange.mutate()
                    .request(newRequest).build());
        }
    }

    @Override
    public int getOrder() {
        return GatewayConstant.Order.AUTH_ORDER;
    }
}
