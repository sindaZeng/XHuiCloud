package com.zsinda.fdp.exception.base;

/**
 * @program: FDPlatform
 * @description: BaseException
 * @author: Sinda
 * @create: 2020-03-16 00:51
 */
public interface BaseException {

    /**
     * 统一参数验证异常码
     */
    int BASE_EXCEPTION_CODE = -1;

    /**
     * 返回异常信息
     *
     * @return
     */
    String getMessage();

    /**
     * 返回异常编码
     *
     * @return
     */
    int getCode();

}
