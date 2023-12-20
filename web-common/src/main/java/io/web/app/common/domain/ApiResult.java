package io.web.app.common.domain;
import io.web.app.common.exception.CommonErrorEnum;
import io.web.app.common.exception.ErrorEnum;
import lombok.Data;

/**
 * Description: 通用返回体
 * Author: <a href="https://github.com/zooways">zooways</a>
 * Date: 2023-03-23
 */
@Data
public class ApiResult<T> {
    /**
     * 成功标识true or false
     */
    private Boolean success;
    /**
     * 错误码
     */
    private Integer errCode;
    /**
     * 错误消息
     */
    private String errMsg;
    /**
     * 返回对象
     */
    private T data;

    public static <T> ApiResult<T> success() {
        ApiResult<T> result = new ApiResult<T>();
        result.setData(null);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> ok() {
        ApiResult<T> result = new ApiResult<T>();
        result.setData(null);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<T>();
        result.setData(data);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> fail(Integer code, String msg) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(code);
        result.setErrMsg(msg);
        return result;
    }

    public static <T> ApiResult<T> fail(ErrorEnum errorEnum) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(errorEnum.getErrorCode());
        result.setErrMsg(errorEnum.getErrorMsg());
        return result;
    }

    public static <T> ApiResult<?> error(String errMsg) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(CommonErrorEnum.SYSTEM_ERROR.getErrorCode());
        result.setErrMsg(errMsg);
        return result;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
