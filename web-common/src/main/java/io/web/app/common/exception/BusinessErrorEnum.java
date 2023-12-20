package io.web.app.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description: 业务校验异常码
 * Author: <a href="https://github.com/zooways">zooways</a>
 * Date: 2023-03-26
 */
@AllArgsConstructor
@Getter
public enum BusinessErrorEnum implements ErrorEnum {
    //==================================common==================================
    BUSINESS_ERROR(1001, "{0}"),
    SYSTEM_ERROR(1001, "系统出小差了，请稍后再试哦~~"),
    ;
    private Integer code;
    private String msg;

    @Override
    public Integer getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}
