package com.xhuicloud.push.service;

import com.xhuicloud.push.common.PushMultiple;
import com.xhuicloud.push.common.PushSingle;

public interface PushService {

    /**
     * 单个消息推送
     *
     * @param pushSingle
     * @return
     */
    boolean pushSingle(PushSingle pushSingle);


    /**
     * 批量消息推送(对多个用户发送同样的模板)
     *
     * @param pushMultiple
     * @return
     */
    boolean pushMultiple(PushMultiple pushMultiple);

}
