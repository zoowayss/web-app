package io.web.app.common.domain;

public class Constants {

    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    public static final String USER_TOKEN_HEADER = "Authorization";

    public static final String ADMIN_TOKEN_HEADER = "Admin-Token";

    public static final long TOKEN_EXPIRE_TIME = 1000l * 60l * 60l * 24l * 7l;

    public static final long ADMIN_TOKEN_EXPIRE_TIME = TOKEN_EXPIRE_TIME;
}
