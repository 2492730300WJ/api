package com.wczx.api.article.filter;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "travelRequestContextFilter")
public class TravelRequestContextFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Map<String, Object> params = new HashMap<>(10);
        params.put("requestUrl", request.getRequestURI());
        params.put("requestMethod", request.getMethod());
        params.put("requestData", request.getParameterMap());
        params.put("operatingId", MDC.get("operatingId"));
        TravelHttpServletRequestWrapper requestWrapper = new TravelHttpServletRequestWrapper(request);
        // 解析放在Body里的参数
        if (request.getParameterMap().size() == 0) {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(requestWrapper.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            String jsonData = responseStrBuilder.toString();
            params.put("requestData", jsonData);
        }
        logger.info("request filter:" + JSON.toJSONString(params));
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
