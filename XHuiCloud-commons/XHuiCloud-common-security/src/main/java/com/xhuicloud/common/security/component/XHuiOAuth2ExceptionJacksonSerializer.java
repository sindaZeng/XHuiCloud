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

package com.xhuicloud.common.security.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.security.exception.XHuiOAuth2Exception;
import java.io.IOException;

/**
 * @program: XHuiCloud
 * @description: 定义异常 ${@link XHuiOAuth2Exception} 的序列化类
 * @author: Sinda
 * @create: 2020-01-01 19:13
 */
public class XHuiOAuth2ExceptionJacksonSerializer extends StdSerializer<XHuiOAuth2Exception> {

    @Override
    public void serialize(XHuiOAuth2Exception e, JsonGenerator generator, SerializerProvider s) throws IOException {
        generator.writeStartObject();
        generator.writeObjectField("code", CommonConstants.REQUEST_FAIL);
        generator.writeStringField("msg", e.getMessage());
        generator.writeStringField("data", e.getErrorCode());
        generator.writeEndObject();
    }

    public XHuiOAuth2ExceptionJacksonSerializer() {
        super(XHuiOAuth2Exception.class);
    }
}
