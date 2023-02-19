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

package com.xhuicloud.common.core.ttl;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

/**
 * @program: XHuiCloud
 * @description: TenantHolder
 * @author: Sinda
 * @create: 2020-05-12 16:42
 */
@UtilityClass
public class XHuiCommonThreadLocalHolder {

    /**
     * 创建本地线程共享对象  租户Id
     */
    private static final ThreadLocal<Long> TENANT = new TransmittableThreadLocal<>();

    private static final ThreadLocal<Long> USER = new TransmittableThreadLocal<>();

    private static final ThreadLocal<String> REQ_ID = new TransmittableThreadLocal<>();

    public void setTenant(Long id) {
        TENANT.set(id);
    }

    public Long getTenant() {
        return TENANT.get();
    }

    public void removeTenant() {
        TENANT.remove();
    }

    public void setUser(Long id) {
        USER.set(id);
    }

    public Long getUser() {
        return USER.get();
    }

    public void removeUser() {
        USER.remove();
    }

    public void setReqId(String id) {
        REQ_ID.set(id);
    }

    public String getReqId() {
        return REQ_ID.get();
    }

    public void removeReqId() {
        REQ_ID.remove();
    }

}
