package io.web.app.server.controller;

import io.web.app.common.annotation.Role;
import io.web.app.common.domain.enums.SysRoleEnum;
import io.web.app.common.token.TokenUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@RestController
@RequestMapping("/test")
@Role({SysRoleEnum.USER,SysRoleEnum.ADMIN})
public class TestController {


    @GetMapping
    public String ping(TokenUser user) {
        return "pong";
    }
}
