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

import com.xhuicloud.common.core.thread.ThreadBroker;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class XHuiTenantThreadBroker {

    /**
     * @param tenant 租户ID
     * @param func
     */

    public void execute(Integer tenant, ThreadBroker.Execute<Integer> func) {
        try {
            func.execute(tenant);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tenant 租户ID
     * @param func
     * @param <R>    返回数据类型
     * @return
     */

    public <R> R submit(Integer tenant, ThreadBroker.Submit<Integer, R> func) {
        try {
            return func.submit(tenant);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param supplier
     * @param func
     */
    public void execute(Supplier<Integer> supplier, ThreadBroker.Execute<Integer> func) {
        execute(supplier.get(), func);
    }

    /**
     * @param supplier
     * @param func
     * @param <R>      返回数据类型
     * @return
     */
    public <R> R submit(Supplier<Integer> supplier, ThreadBroker.Submit<Integer, R> func) {
        return submit(supplier.get(), func);
    }

}
