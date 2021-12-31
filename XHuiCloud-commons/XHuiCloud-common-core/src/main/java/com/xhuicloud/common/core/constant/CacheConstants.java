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
 * @description: CacheConstants
 * @author: Sinda
 * @create: 2020-06-17 15:50
 */
public interface CacheConstants {

    /**
     * 系统参数
     */
    String SYS_PARAM = "SYS:PARAM:";

    /**
     * 用戶
     */
    String SYS_USER = "SYS:USER:";

    /**
     * 部门
     */
    String SYS_DEPT = "SYS:DEPT:";

    /**
     * 角色
     */
    String SYS_ROLE = "SYS:ROLE:";

    /**
     * 字典项
     */
    String SYS_DICT = "SYS:DICT:";

    /**
     * 字典项
     */
    String SYS_DICT_DATA = "SYS:DICT:DATA:";

    /**
     * 客户端信息
     */
    String CLIENT_DETAILS = "CLIENT:DETAILS:";

    /**
     * 推送模板
     */
    String PUSH_TEMPLATE = "PUSH:TEMPLATE:";

    /**
     * 推送模板组
     */
    String PUSH_GROUP = "PUSH:GROUP:";

    /**
     * 全局缓存，该缓存不区分租户
     */
    String GLOBALLY = "gl:";

}
