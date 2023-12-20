package io.web.app.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@SpringBootApplication(scanBasePackages = "io.web.app.**")
@MapperScan(basePackages = "io.web.app.*.mapper.**")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
