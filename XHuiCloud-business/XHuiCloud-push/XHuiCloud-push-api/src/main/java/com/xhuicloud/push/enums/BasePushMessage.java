package com.xhuicloud.push.enums;

import com.xhuicloud.push.common.PushSingle;

import java.util.Map;

public interface BasePushMessage {

    ;

    /**
     * 模板参数名
     * @return
     */
    String[] paramNames();

    /**
     * 获取单一推送数据载体
     */
    PushSingle getPushSingle(Map<String, String> params);


}
