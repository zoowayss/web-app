package io.web.app.server.config;

import cn.hutool.extra.spring.SpringUtil;
import io.web.app.common.service.impl.UserServiceImpl;
import io.web.app.common.token.CollectorInterceptor;
import io.web.app.common.token.TokenHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public TokenHandlerInterceptor tokenHandlerInterceptor(UserServiceImpl userService) {
        return new TokenHandlerInterceptor(userService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(SpringUtil.getBean("tokenHandlerInterceptor")).excludePathPatterns("/user/login/**");
        registry.addInterceptor(new CollectorInterceptor()).excludePathPatterns("/**");
    }
}
