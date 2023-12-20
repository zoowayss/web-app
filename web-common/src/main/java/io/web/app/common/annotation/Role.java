package io.web.app.common.annotation;

import io.web.app.common.domain.enums.SysRoleEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Role {

    SysRoleEnum[] value();
}
