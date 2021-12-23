package com.xhuicloud.common.core.exception;

import com.xhuicloud.common.core.exception.base.BaseRuntimeException;

/**
 * @program: XHuiCloud
 * @description: ValidateCodeException
 * @author: Sinda
 * @create: 2020-06-17 10:45
 */
public class ValidateException extends BaseRuntimeException {

    /**
     * 系统错误异常
     *
     * @param message
     * @param args
     * @return
     */
    public static ValidateException validateFail(String message, Object... args) {
        return custom(BASE_EXCEPTION_CODE, message, args);
    }

    /**
     * 自定义错误异常
     *
     * @param code
     * @param message
     * @param args
     * @return
     */
    public static ValidateException custom(int code, String message, Object... args) {
        return new ValidateException(code, message, args);
    }

    public ValidateException(int code, String message, Object... args) {
        super(code, message, args);
    }

    public ValidateException(int code, String message) {
        super(code, message);
    }

}
