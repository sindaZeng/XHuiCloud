package com.xhuicloud.common.mybatis.meta;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xhuicloud.common.data.tenant.XHuiCommonThreadLocalHolder;
import org.apache.ibatis.reflection.MetaObject;

public class AutoFieldMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建
     */
    private static final String CREATER_ID = "createId";

    /**
     * 更新
     */
    private static final String UPDATE_ID = "updateId";

    @Override
    public void insertFill(MetaObject metaObject) {
        Integer userId = XHuiCommonThreadLocalHolder.getUserId();
        if (userId != null)
            this.setFieldValByName(CREATER_ID, userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Integer userId = XHuiCommonThreadLocalHolder.getUserId();
        if (userId != null)
            this.setFieldValByName(UPDATE_ID, userId, metaObject);
    }
}
