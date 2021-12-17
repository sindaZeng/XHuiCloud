package com.xhuicloud.push.enums;

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
    public String url() {
        return null;
    }

    @Override
    public String source() {
        return null;
    }

    @Override
    public String content() {
        return null;
    }

    @Override
    public String templateId() {
        return null;
    }

}
