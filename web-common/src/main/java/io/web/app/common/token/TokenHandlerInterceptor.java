package io.web.app.common.token;

import io.web.app.common.annotation.Role;
import io.web.app.common.domain.enums.SysRoleEnum;
import io.web.app.common.entity.UserEntity;
import io.web.app.common.service.UserService;
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

    private TokenVerifier verifier;

    @Resource
    private UserService userService;

    public TokenHandlerInterceptor(TokenVerifier tokenVerifier) {
        this.verifier = tokenVerifier;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod handlerMethod) {

            List<String> roles = new ArrayList<>();
            Role annotation = handlerMethod.getMethodAnnotation(Role.class);
            if (annotation != null) {
                roles.addAll(Arrays.stream(annotation.value()).map(SysRoleEnum::getCode).toList());
            }
            annotation = handlerMethod.getBeanType().getAnnotation(Role.class);
            if (annotation != null) {
                roles.addAll(Arrays.stream(annotation.value()).map(SysRoleEnum::getCode).toList());
            }

            if (CollectionUtils.isEmpty(roles)) {
                return true;
            }
            String token = request.getHeader(tokenHeaderName);
            String userId;
            if ((token == null || (userId = verifier.verifyAndGetUserId(token)) == null)) {
                response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(unauthorizedPrompt);
                return false;
            }
            UserEntity userEntity = userService.getById(userId);

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

        return true;
    }
}
