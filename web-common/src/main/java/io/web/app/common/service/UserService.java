package io.web.app.common.service;

import io.web.app.common.domain.enums.SysRoleEnum;
import io.web.app.common.entity.UserEntity;

import java.util.List;

public interface UserService {

   String generateJwt(UserEntity user);

   UserEntity getUserByUsername(String username);

   UserEntity saveOne(UserEntity registerUser, List<SysRoleEnum> user);

   UserEntity getById(String userId);
}
