package com.zsinda.fdp.exception;

import com.zsinda.fdp.exception.base.BaseRuntimeException;

/**
 * @program: FDPlatform
 * @description: 业务异常，系统异常
 * @author: Sinda
 * @create: 2020-03-16 00:58
 */
public class SysException extends BaseRuntimeException {

    /**
     *  系统错误异常
     * @param message
     * @param args
     * @return
     */
    public static SysException sysFail(String message, Object... args) {
        return new SysException(BASE_EXCEPTION_CODE, message, args);
    }

    /**
     * 自定义错误异常
     * @param code
     * @param message
     * @param args
     * @return
     */
    public static SysException custom(int code, String message, Object... args) {
        return new SysException(code, message, args);
    }
    public SysException(int code, String message, Object... args) {
        super(code, message, args);
    }

    public SysException(int code, String message) {
        super(code, message);
    }

}
