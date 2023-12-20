package io.web.app.common.token;

public interface TokenVerifier {

    String verifyAndGetUserId(String token);

}