package com.xhuicloud.common.mq.constant;

public interface XHuiRabbitMqConstant {

    /**
     * 错误队列的前缀
     */
    String ERROR_QUEUE_PREFIX = "error.";

    /**
     * Binding bean 后缀名
     */
    String BINDING_SUFFIX = "Binding";

    /**
     * direct Binding bean 后缀名
     */
    String DIRECT_BINDING_SUFFIX = "Direct" + BINDING_SUFFIX;

    /**
     * delayed Binding bean 后缀名
     */
    String DELAYED_BINDING_SUFFIX = "Delayed" + BINDING_SUFFIX;

}
