package com.xhuicloud.wechat.config;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/11/12
 */
@UtilityClass
public class WeChatAppIdContextHolder {

    private final ThreadLocal<String> APPID = new TransmittableThreadLocal<>();


    public void setAppId(String appId) {
        APPID.set(appId);
    }

    public String getAppId() {
        return APPID.get();
    }

    public void remove() {
        APPID.remove();
    }
}
