package com.xhuicloud.common.data.tenant;

import cn.hutool.extra.spring.SpringUtil;
import com.xhuicloud.common.core.thread.AsyncThreadExecutePool;
import com.xhuicloud.common.core.thread.ThreadBroker;
import lombok.experimental.UtilityClass;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.function.Supplier;

@UtilityClass
public class XHuiTenantThreadBroker {

    /**
     * @param tenant 租户ID
     * @param func
     */
    public static void asyncExecute(Integer tenant, ThreadBroker.Execute<Integer> func) {
        XHuiCommonThreadLocalHolder.setTenant(tenant);
        try {
            AsyncThreadExecutePool asyncThreadExecutePool = SpringUtil.getBean(AsyncThreadExecutePool.class);
            if (asyncThreadExecutePool != null && asyncThreadExecutePool.getAsyncExecutor() != null) {
                asyncThreadExecutePool.getAsyncExecutor()
                        .execute(() -> {
                            try {
                                func.execute(tenant);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
            } else {
                func.execute(tenant);
            }
        } catch (Exception e) {
            throw new SecurityException(e.getMessage(), e);
        }
    }

    /**
     * @param tenant 租户ID
     * @param func
     * @param <R>    返回数据类型
     * @return
     */
    public static <R> R asyncSubmit(Integer tenant, ThreadBroker.Submit<Integer, R> func) {
        XHuiCommonThreadLocalHolder.setTenant(tenant);
        try {
            AsyncThreadExecutePool asyncThreadExecutePool = SpringUtil.getBean(AsyncThreadExecutePool.class);
            if (asyncThreadExecutePool != null && asyncThreadExecutePool.getAsyncExecutor() != null) {
                ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) asyncThreadExecutePool.getAsyncExecutor();
                return (R) threadPoolTaskExecutor.submit(() -> {
                    try {
                        func.submit(tenant);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).get();
            } else {
                return func.submit(tenant);
            }
        } catch (Exception e) {
            throw new SecurityException(e.getMessage(), e);
        }
    }

    /**
     * @param supplier
     * @param func
     */
    public static void asyncExecute(Supplier<Integer> supplier, ThreadBroker.Execute<Integer> func) {
        asyncExecute(supplier.get(), func);
    }

    /**
     * @param supplier
     * @param func
     * @param <R>      返回数据类型
     * @return
     */
    public static <R> R asyncSubmit(Supplier<Integer> supplier, ThreadBroker.Submit<Integer, R> func) {
        return asyncSubmit(supplier.get(), func);
    }

}
