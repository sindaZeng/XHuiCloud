package com.xhuicloud.common.core.exception;

import com.xhuicloud.common.core.exception.base.BaseRuntimeException;

/**
 * @program: XHuiCloud
 * @description: ValidateCodeException
 * @author: Sinda
 * @create: 2020-06-17 10:45
 */
public class ValidateCodeException extends BaseRuntimeException {

    public static final String CODE_VALIDATE_FAIL = "验证码错误!";

    public static final String CODE_IS_NULL_FAIL = "验证码不能为空!";

    public static final String MOBILE_IS_NULL_FAIL = "手机号不能为空!";

    public static final String CODE_ILLEGAL_FAIL = "验证码不合法!";

    public static final String CODE_OVERDUE_FAIL = "验证码已过期!";

    /**
     * 系统错误异常
     *
     * @param message
     * @param args
     * @return
     */
    public static ValidateCodeException validateFail(String message, Object... args) {
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
    public static ValidateCodeException custom(int code, String message, Object... args) {
        return new ValidateCodeException(code, message, args);
    }

    public ValidateCodeException(int code, String message, Object... args) {
        super(code, message, args);
    }

    public ValidateCodeException(int code, String message) {
        super(code, message);
    }

}
