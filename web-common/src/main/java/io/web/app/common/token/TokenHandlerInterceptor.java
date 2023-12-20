package io.web.app.common.token;

import io.web.app.common.service.UserService;
import io.web.app.common.annotation.Role;
import io.web.app.common.domain.enums.SysRoleEnum;
import io.web.app.common.entity.UserEntity;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenHandlerInterceptor implements HandlerInterceptor {
    public static final String ATTRIBUTE_UID = "uid";

    public String tokenHeaderName = "Authorization";
    private String unauthorizedPrompt = "{\"success\":false, \"code\":\"401\", \"msg\": \"Unauthorized\"}";
    private boolean interceptByDefault = false;

    private TokenVerifier verifier;

    @Resource
    private UserService userService;

    public TokenHandlerInterceptor(TokenVerifier tokenVerifier) {
        this.verifier = tokenVerifier;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod handlerMethod) {

            boolean flag = interceptByDefault; // 是否默认校验token
            if (!flag) {
                for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
                    if (methodParameter.getParameterType().isAssignableFrom(TokenUser.class)) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) {
                String token = request.getHeader(tokenHeaderName);
                String userId;
                if ((token == null || (userId = verifier.verifyAndGetUserId(token)) == null)) {
                    response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write(unauthorizedPrompt);
                    return false;
                }
                UserEntity userEntity = userService.getById(userId);

                List<String> roles = new ArrayList<>();
                Role annotation = handlerMethod.getMethodAnnotation(Role.class);
                if (annotation != null) {
                    roles.addAll(Arrays.stream(annotation.value()).map(SysRoleEnum::getCode).toList());
                }
                annotation = handlerMethod.getBeanType().getAnnotation(Role.class);
                if (annotation != null) {
                    roles.addAll(Arrays.stream(annotation.value()).map(SysRoleEnum::getCode).toList());
                }
                if (!CollectionUtils.isEmpty(roles)) {
                    for (String role : roles) {
                        if (userEntity.getRole().contains(role)) {
                            request.setAttribute(ATTRIBUTE_UID, userId);
                            return true;
                        }
                    }
                    response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write(unauthorizedPrompt);
                    return false;
                }
                request.setAttribute(ATTRIBUTE_UID, userId);
                return true;
            }
        }
        return true;

    }
}
