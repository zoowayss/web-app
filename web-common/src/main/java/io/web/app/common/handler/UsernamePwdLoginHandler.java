package io.web.app.common.handler;

import cn.hutool.crypto.digest.BCrypt;
import io.web.app.common.service.UserService;
import io.web.app.common.domain.LoginRequest;
import io.web.app.common.domain.enums.SysRoleEnum;
import io.web.app.common.entity.UserEntity;
import io.web.app.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Service
public class UsernamePwdLoginHandler implements LoginMethodHandler {
    @Resource
    private UserService userService;

    @Override
    public String supportLoginMethod() {
        return LoginRequest.LOGIN_METHOD_USERNAME;
    }

    @Transactional
    @Override
    public UserEntity login(LoginRequest loginRequest) {
        if (!StringUtils.hasText(loginRequest.getUsername())) {
            throw new BusinessException("username can not be empty");
        }
        if (!StringUtils.hasText(loginRequest.getPassword())) {
            throw new BusinessException("password can not be empty");
        }

        UserEntity userEntity = userService.getUserByUsername(loginRequest.getUsername());

        if (userEntity == null && loginRequest.isAutoReg()) {
            return userService.saveOne(getRegisterUser(loginRequest), List.of(SysRoleEnum.USER));
        }

        if (userEntity != null) {
            if (BCrypt.checkpw(loginRequest.getPassword(), userEntity.getPassword())) {
                return userEntity;
            } else {
                throw new BusinessException("password is wrong");
            }
        }

        throw new BusinessException("username is not exist");
    }

    private UserEntity getRegisterUser(LoginRequest loginRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(loginRequest.getUsername());
        userEntity.setPassword(loginRequest.getPassword());
        return userEntity;
    }
}
