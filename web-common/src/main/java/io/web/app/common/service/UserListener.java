package io.web.app.common.service;

import io.web.app.common.entity.UserEntity;

/**
 * @Description:
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/12/20 13:08
 */

public interface UserListener {

    void onRegister(UserEntity userEntity);
}
