package io.web.app.common.token;

import cn.hutool.extra.servlet.ServletUtil;
import io.web.app.common.domain.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 信息收集的拦截器
 */
@Order(1)
@Slf4j
public class CollectorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestHolder.RequestInfo info = new RequestHolder.RequestInfo();
        info.setUid(Optional.ofNullable(request.getAttribute(TokenHandlerInterceptor.ATTRIBUTE_UID)).map(Object::toString).map(Object::toString).orElse(null));
        info.setIp(ServletUtil.getClientIP(request));
        RequestHolder.set(info);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
    }

}