package com.xhuicloud.push.enums;
import java.util.Date;

import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.push.common.BasePushData.Parameter;
import java.util.Map;

import com.xhuicloud.push.common.PushSingle;

public enum WeChatMpMessage implements BasePushMessage{

    /**
     * {first.DATA}登录账号：{keyword1.DATA}登录APP：{keyword2.DATA}登录时间：{keyword3.DATA}登录地点：{keyword4.DATA}{remark.DATA}
     */
    LOGIN_SUCCESS {

        @Override
        public String[] paramNames() {
            return new String[]{"first.DATA","keyword1.DATA","keyword2.DATA","keyword3.DATA","keyword4.DATA","remark.DATA"};
        }

    },

    ;

    @Override
    public String[] paramNames() {
        return new String[0];
    }

    @Override
    public PushSingle getPushSingle(Map<String, String> params) {
        PushSingle pushSingle = new PushSingle();
        pushSingle.setTemplateCode(name());
        for (String k : paramNames()) {
            String v = params.get(k);
            if (StrUtil.isBlank(v)) {
                throw SysException.sysFail("推送参数错误, key:%s", k);
            }
            Parameter parameter = new Parameter();
            parameter.setKey(k);
            parameter.setValue(v);
            pushSingle.add(parameter);
        }
        pushSingle.setSendTime(new Date());
        return pushSingle;
    }

}
