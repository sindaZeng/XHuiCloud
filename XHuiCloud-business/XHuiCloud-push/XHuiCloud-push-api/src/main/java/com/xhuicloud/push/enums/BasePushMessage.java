package com.xhuicloud.push.enums;

import java.util.Map;

public interface BasePushMessage {

    ;

    /**
     * 模板参数名字
     * @return
     */
    String[] paramNames();

    /**
     * 跳转链接
     * @return
     */
    String url();

    /**
     * 消息来源
     * @return
     */
    String source();

    /**
     * 微信公众推送内容
     * @return
     */
    String content();

    /**
     * 模板id
     * @return
     */
    String templateId();

}
