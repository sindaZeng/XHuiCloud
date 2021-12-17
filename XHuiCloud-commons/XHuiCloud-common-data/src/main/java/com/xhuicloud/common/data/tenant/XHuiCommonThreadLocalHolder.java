package com.xhuicloud.common.data.tenant;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @program: XHuiCloud
 * @description: TenantHolder
 * @author: Sinda
 * @create: 2020-05-12 16:42
 */
public class XHuiCommonThreadLocalHolder {

    /**
     * 创建本地线程共享对象  租户Id
     */
    private static final ThreadLocal<Integer> TENANT = new TransmittableThreadLocal<>();

    /**
     * 创建本地线程共享对象  用户Id
     */
    private static final ThreadLocal<Integer> USERID = new TransmittableThreadLocal<>();

    public static void setTenant(Integer id) {
        TENANT.set(id);
    }

    public static Integer getTenant() {
        return TENANT.get();
    }

    public static void removeTenant() {
        TENANT.remove();
    }

    public static void removeUserId() {
        USERID.remove();
    }

    public static void setUserId(Integer id) {
        USERID.set(id);
    }

    public static Integer getUserId() {
        return USERID.get();
    }

}
