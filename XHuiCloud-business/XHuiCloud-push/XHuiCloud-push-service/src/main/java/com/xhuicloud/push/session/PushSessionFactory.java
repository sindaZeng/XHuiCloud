package com.xhuicloud.push.session;

import com.xhuicloud.push.enums.PushTypeEnum;

/**
 * 推送工厂
 */
public interface PushSessionFactory {

    PushSession getPushSession(PushTypeEnum pushTypeEnum);

    PushSession getPushSession(String type);

    PushTypeEnum getPushTypeEnum();

}
