package io.web.app.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.web.app.common.service.UserService;
import io.web.app.common.domain.Constants;
import io.web.app.common.domain.enums.SysRoleEnum;
import io.web.app.common.entity.UserEntity;
import io.web.app.common.mapper.UserMapper;
import io.web.app.common.service.UserInfoAddr;
import io.web.app.common.service.UserListener;
import io.web.app.common.token.TokenVerifier;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService, InitializingBean, UserInfoAddr, TokenVerifier {
    @Value("${core.user.jwtSecret:secret}")
    private String secret;
    private Algorithm algorithm; //use more secure key
    private JWTVerifier verifier; //Reusable verifier instance

    @Resource
    @Lazy
    List<UserListener> userListenerList;

    @Override
    public String generateJwt(UserEntity user) {

        String token = null;
        try {
            token = JWT.create()
                    .withClaim("id", user.getId())
                    .withExpiresAt(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRE_TIME))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            //Invalid Signing configuration / Couldn't convert Claims.
            log.error("{}", e);
        }

        return token;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return getOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUsername, username));
    }

    @Override
    public UserEntity saveOne(UserEntity registerUser, List<SysRoleEnum> roles) {
        registerUser.setRole(roles.stream().map(SysRoleEnum::getCode).toList());
        registerUser.setPassword(BCrypt.hashpw(registerUser.getPassword()));
        save(registerUser);
        for (UserListener listener : userListenerList) {
            listener.onRegister(registerUser);
        }
        return getById(registerUser.getId());
    }

    @Override
    public UserEntity getById(String userId) {
        return super.getById(userId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
    }

    @Override
    public void add(UserEntity user, Map<String, Object> data) {
        UserEntity byId = getById(user.getId());
        byId.setPassword(null);
        data.putAll(BeanUtil.beanToMap(byId));
    }

    @Override
    public String verifyAndGetUserId(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            String id = decodedJWT.getClaim("id").asString();
            return id;
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
