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

package com.xhuicloud.common.core.constant;

/**
 * @program: XHuiCloud
 * @description: SecurityConstants
 * @author: Sinda
 * @create: 2020-06-17 09:59
 */
public interface SecurityConstants {

    /**
     * 验证码前缀
     */
    String CODE_KEY = "XHUI_SMS_CODE_KEY";

    /**
     * 验证码有效期
     */
    Long CODE_TIME = 60L;

    /**
     * OAUTH
     */
    String OAUTH_TOKEN = "/oauth2/token";

    /**
     * 第三方社交登录
     */
    String TOKEN_SOCIAL = "/token/social/*";

    /**
     * 刷新TOKEN
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * 微信公众号扫码
     */
    String WECHAT_MP_SCAN = "wechat:mp:scan:";

    /**
     * 微信公众号扫码 用户成功扫码
     */
    String WECHAT_MP_SCAN_SUCCESS = "wechat:mp:scan:success:";

}
