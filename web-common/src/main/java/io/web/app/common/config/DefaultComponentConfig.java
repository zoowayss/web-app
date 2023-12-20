package io.web.app.common.config;

import io.web.app.common.service.UserInfoAddr;
import io.web.app.common.service.UserListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Configuration
public class DefaultComponentConfig {


    @Bean
    @ConditionalOnMissingBean(UserInfoAddr.class)
    public UserInfoAddr defaultUserInfoAddr() {
        return (user, data) -> {
            // do nothing
        };
    }

    @Bean
    @ConditionalOnMissingBean(UserListener.class)
    public UserListener defaultUserListener() {
        return user -> {
            // do nothing
        };
    }
}
