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
