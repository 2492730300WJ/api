package com.wczx.api.gateway.config.jwt;

import com.wczx.api.gateway.config.response.WorkException;
import com.wczx.api.gateway.config.response.WorkStatus;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Administrator
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证 先校验短token
                String refreshToken = httpServletRequest.getHeader("refreshToken");
                if (token == null) {
                    // 取出长token
                    if (refreshToken == null) {
                        throw new WorkException(WorkStatus.PLEASE_LOGIN);
                    }
                    // 存在长token刷新短token
                    try {
                        Claims c = tokenService.parseRefreshJWT(refreshToken);
                        System.out.println(c.getSubject());
                    } catch (Exception e) {
                        throw new WorkException(WorkStatus.LOGIN_TIME_OUT);
                    }
                }
                //注意：如果jwt已经过期了，这里会抛出jwt过期异常
                try {
                    Claims c = tokenService.parseJWT(token);
                    System.out.println(c.getSubject());
                } catch (Exception e) {
                    throw new WorkException(WorkStatus.LOGIN_TIME_OUT);
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
