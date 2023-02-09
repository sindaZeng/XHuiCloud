/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.push.enums;

import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.core.exception.SysException;
import com.xhuicloud.push.common.BasePushData.Parameter;
import com.xhuicloud.push.common.PushSingle;

import java.util.Date;
import java.util.Map;

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
                SysException.sysFail("推送参数错误, key:%s", k);
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
