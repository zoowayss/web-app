package io.web.app.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    public static final String LOGIN_METHOD_USERNAME = "username";
    public static final String LOGIN_METHOD_MOBILE = "mobile";
    public static final String LOGIN_METHOD_EMAIL = "email";
    private String loginMethod;

    private String username;

    private String password;

    private String mobile;

    private String email;

    private String captchaId;

    private String captchaValue;

    private boolean autoReg;
}
