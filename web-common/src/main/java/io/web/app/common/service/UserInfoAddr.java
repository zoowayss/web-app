package io.web.app.common.service;

import io.web.app.common.entity.UserEntity;

import java.util.Map;

/**
 * @Description:
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/12/20 12:51
 */

public interface UserInfoAddr {

    void add(UserEntity user, Map<String, Object> data);
}
