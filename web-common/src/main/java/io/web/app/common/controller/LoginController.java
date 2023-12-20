package io.web.app.common.controller;

import io.web.app.common.service.UserService;
import io.web.app.common.domain.ApiResult;
import io.web.app.common.domain.LoginRequest;
import io.web.app.common.entity.UserEntity;
import io.web.app.common.service.LoginService;
import io.web.app.common.service.UserInfoAddr;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@RequestMapping("/user/login")
@RestController
public class LoginController {


    @Resource
    private LoginService loginService;

    @Lazy
    @Resource
    private List<UserInfoAddr> userInfoAddrList;
    @Resource
    private UserService userService;

    @PostMapping
    public ApiResult<?> login(@RequestBody LoginRequest loginRequest) {
        UserEntity user = loginService.login(loginRequest);
        Map<String, Object> ans = new HashMap<>();
        ans.put("token", userService.generateJwt(user));
        Map<String, Object> data = new HashMap<>();
        ans.put("info", data);
        userInfoAddrList.forEach(userInfoAddr -> userInfoAddr.add(user, data));
        Iterator<?> iter = data.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
            if (entry.getValue() == null) {
                iter.remove(); // 移除值为 null 的键值对
            }
        }
        return ApiResult.success(ans);
    }

}
