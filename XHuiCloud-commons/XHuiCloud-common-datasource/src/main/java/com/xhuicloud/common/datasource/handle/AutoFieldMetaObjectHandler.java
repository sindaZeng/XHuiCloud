package com.xhuicloud.common.datasource.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xhuicloud.common.security.service.XHuiUser;
import com.xhuicloud.common.security.utils.SecurityHolder;
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
        XHuiUser user = SecurityHolder.getUser();
        if (user != null)
            this.setFieldValByName(CREATER_ID, user.getId(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        XHuiUser user = SecurityHolder.getUser();
        if (user != null)
            this.setFieldValByName(UPDATE_ID, user.getId(), metaObject);
    }
}
