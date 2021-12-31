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
