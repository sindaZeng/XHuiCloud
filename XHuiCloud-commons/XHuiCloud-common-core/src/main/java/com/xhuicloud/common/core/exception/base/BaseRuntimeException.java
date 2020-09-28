package com.xhuicloud.common.core.exception.base;

/**
 * @program: XHuiCloud
 * @description: BaseRuntimeException
 * @author: Sinda
 * @create: 2020-03-16 00:55
 */
public class BaseRuntimeException extends RuntimeException implements BaseException{

    /**
     * 异常信息
     */
    protected String message;

    /**
     * 具体异常码
     */
    protected int code;

    public BaseRuntimeException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseRuntimeException(int code, String format, Object... args) {
        super(String.format(format, args));
        this.code = code;
        this.message = String.format(format, args);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }

}
