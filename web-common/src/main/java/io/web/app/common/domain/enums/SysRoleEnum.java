package io.web.app.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/12/20 12:34
 */
@Getter
@AllArgsConstructor
public enum SysRoleEnum {

    SUPER_ADMIN("超级管理员", "super_admin"),
    ADMIN("管理员", "admin"), USER("普通用户", "user");
    private String name;
    private String code;

}
