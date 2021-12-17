package com.xhuicloud.common.data.resolver;

import com.xhuicloud.common.core.utils.KeyStrResolver;
import com.xhuicloud.common.data.tenant.XHuiCommonThreadLocalHolder;

public class TenantKeyResolver implements KeyStrResolver {

    /**
     * 传入字符串增加 租户编号:in
     * @param in 输入字符串
     * @param split 分割符
     * @return
     */
    @Override
    public String extract(String in, String split) {
        return XHuiCommonThreadLocalHolder.getTenant() + split + in;
    }

    /**
     * 返回当前租户ID
     * @return
     */
    @Override
    public String key() {
        return String.valueOf(XHuiCommonThreadLocalHolder.getTenant());
    }

}
