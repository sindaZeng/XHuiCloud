package com.xhuicloud.push.enums;


import com.xhuicloud.push.common.PushSingle;

import java.util.List;
import java.util.Map;

public enum SmsMessage implements BasePushMessage {

    VERIFICATION_CODE{

    },

    ;


    @Override
    public String[] paramNames() {
        return new String[0];
    }

    @Override
    public PushSingle setPushSingle(Map<String, String> params) {
        return null;
    }


}
