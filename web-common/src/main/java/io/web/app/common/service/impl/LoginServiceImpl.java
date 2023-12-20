package io.web.app.common.service.impl;

import io.web.app.common.domain.LoginRequest;
import io.web.app.common.entity.UserEntity;
import io.web.app.common.exception.BusinessException;
import io.web.app.common.handler.LoginMethodHandler;
import io.web.app.common.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Service
public class LoginServiceImpl implements LoginService {

    private Map<String, LoginMethodHandler> loginMethodHandlerMap;

    @Autowired
    public void setLoginMethodHandlerMap(List<LoginMethodHandler> handlerList) {
        this.loginMethodHandlerMap = handlerList.stream().collect(Collectors.toMap(LoginMethodHandler::supportLoginMethod, Function.identity()));
    }

    @Override
    public UserEntity login(LoginRequest loginRequest) {
        LoginMethodHandler handler = loginMethodHandlerMap.get(loginRequest.getLoginMethod());
        if (handler == null) {
            throw new BusinessException("current login method:" + loginRequest.getLoginMethod() + " is not supported");
        }
        return handler.login(loginRequest);
    }
}
