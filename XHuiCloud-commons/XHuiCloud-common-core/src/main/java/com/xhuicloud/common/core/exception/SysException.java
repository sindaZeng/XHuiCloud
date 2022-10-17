/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.common.core.exception;

import com.xhuicloud.common.core.exception.base.BaseRuntimeException;

/**
 * @program: XHuiCloud
 * @description: 业务异常，系统异常
 * @author: Sinda
 * @create: 2020-03-16 00:58
 */
public class SysException extends BaseRuntimeException {

    public static final String USER_NOT_EXIST_DATA_EXCEPTION="该用户不存在,数据出现错误!请联系管理员!";

    public static final String TENANT_NOT_EXIST_DATA_EXCEPTION="该租户不存在,数据出现错误!请联系管理员!";

    public static final String USER_IS_EXIST_EXCEPTION="该用户已存在,数据出现错误!请联系管理员!";

    public static final String PARAM_IS_EXIST_DATA_EXCEPTION="该参数已存在!";

    public static final String MOBILE_IS_ALREADY_BOUND="该手机已被绑定!";

    public static final String PARAM_EXCEPTION="参数出现错误, 请重试!";

    /**
     *  系统错误异常
     * @param message
     * @param args
     * @return
     */
    public static SysException sysFail(String message, Object... args) {
        return custom(BASE_EXCEPTION_CODE, message, args);
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
