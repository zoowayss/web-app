package io.web.app.common.service;

import io.web.app.common.domain.LoginRequest;
import io.web.app.common.entity.UserEntity;

/**
 * @Description:
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/12/20 12:46
 */

public interface LoginService {
    UserEntity login(LoginRequest loginRequest);
}
