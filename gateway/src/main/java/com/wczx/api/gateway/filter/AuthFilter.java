package com.wczx.api.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wczx.api.common.constant.GatewayConstant;
import com.wczx.api.common.dto.request.auth.AuthRequest;
import com.wczx.api.common.response.WorkException;
import com.wczx.api.common.response.WorkResponse;
import com.wczx.api.common.response.WorkStatus;
import com.wczx.api.feign.client.AuthClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
        String url = exchange.getRequest().getURI().getPath();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        try {
            String scheme = exchange.getRequest().getURI().getScheme();
            String host = exchange.getRequest().getURI().getHost();
            int port = exchange.getRequest().getURI().getPort();
            String basePath = scheme + "://" + host + ":" + port;

            List<String> whiteUrl = new ArrayList<>();
            whiteUrl.add("/user/login");

            log.info("request filter:" + basePath + url);
            //跳过白名单
            if (null != whiteUrl && whiteUrl.contains(url)) {
                return chain.filter(exchange);
            }
            if (url.contains("/ws/websocket/")) {
                System.out.println("socket跳出");
                return chain.filter(exchange);
            }

            // 获取权限校验部分
            String authHeader = exchange.getRequest().getHeaders().getFirst(GatewayConstant.AUTH_HEADER);
            if (StringUtils.isBlank(authHeader)) {
                throw new WorkException(WorkStatus.AUTH_ERROR);
            }
            AuthRequest authRequest = new AuthRequest();
            authRequest.setToken(authHeader);
            authRequest.setHttpMethod(request.getMethodValue());
            authRequest.setUri(url);
            WorkResponse workResponse = authClient.auth(authRequest);
            if (!workResponse.getCode().equals(WorkStatus.SUCCESS.getWorkCode())) {
                throw new WorkException(workResponse.getCode(), workResponse.getMsg());
            }
            ServerHttpRequest.Builder mutate = exchange.getRequest().mutate();
            mutate.header("sessionInfo", workResponse.getData().toString());
            ServerHttpRequest buildRequest = mutate.build();
            return chain.filter(exchange.mutate().request(buildRequest).build());
        } catch (WorkException w) {
            return authError(response, w);
        }
    }

    /**
     * 权限异常
     * @return
     */
    private Mono<Void> authError(ServerHttpResponse resp, WorkException w) {
        resp.setStatusCode(HttpStatus.OK);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        WorkResponse returnData = new WorkResponse(w.getExceptionCode(), w.getExceptionMsg());
        String returnStr = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            returnStr = objectMapper.writeValueAsString(returnData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return GatewayConstant.Order.AUTH_ORDER;
    }
}
