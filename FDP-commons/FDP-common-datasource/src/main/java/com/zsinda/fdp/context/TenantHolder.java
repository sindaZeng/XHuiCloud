package com.zsinda.fdp.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @program: FDPlatform
 * @description: TenantHolder
 * @author: Sinda
 * @create: 2020-05-12 16:42
 */
public class TenantHolder {

    /**
     * 创建本地线程共享对象  租户Id
     */
    private static final ThreadLocal<Integer> TENANT_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void setTenant(Integer id) {
        TENANT_THREAD_LOCAL.set(id);
    }

    public static Integer getTenant() {
        return TENANT_THREAD_LOCAL.get();
    }

    public static void removeTenant() {
        TENANT_THREAD_LOCAL.remove();
    }

}
