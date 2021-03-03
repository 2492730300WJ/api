package com.wczx.api.user.listener;


import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.util.UUID;

/**
 * @author Administrator
 */
@Component
@WebListener
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent arg0) {
        MDC.put("operatingId", String.valueOf(UUID.randomUUID()));
    }

    @Override
    public void requestDestroyed(ServletRequestEvent arg0) {
        MDC.clear();
    }
}
