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

package com.xhuicloud.common.mybatis.meta;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xhuicloud.common.core.ttl.XHuiCommonThreadLocalHolder;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

public class AutoFieldMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建人
     */
    private static final String CREATER_ID = "createId";

    /**
     * 创建时间
     */
    private static final String CREATE_TIME = "createTime";

    /**
     * 更新人
     */
    private static final String UPDATE_ID = "updateId";

    /**
     * 更新时间
     */
    private static final String UPDATE_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        set(metaObject, CREATER_ID, CREATE_TIME);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        set(metaObject, UPDATE_ID, UPDATE_TIME);
    }

    private void set(MetaObject metaObject, String field, String field2) {
        Object fieldObj = metaObject.getValue(field);
        if (fieldObj == null) {
            Long userId = XHuiCommonThreadLocalHolder.getUser();
            if (userId != null) {
                this.setFieldValByName(field, userId, metaObject);
            }
        }

        Object field2Obj = metaObject.getValue(field2);
        if (field2Obj == null) {
            LocalDateTime now = LocalDateTime.now();
            this.setFieldValByName(field2, now, metaObject);
        }
    }
}
