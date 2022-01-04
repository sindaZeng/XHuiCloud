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
