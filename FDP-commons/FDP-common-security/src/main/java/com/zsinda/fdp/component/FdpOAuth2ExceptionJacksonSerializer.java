package com.zsinda.fdp.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.zsinda.fdp.content.CommonContent;
import com.zsinda.fdp.exception.FdpOAuth2Exception;

import java.io.IOException;

/**
 * @program: FDPlatform
 * @description: 定义异常 ${@link FdpOAuth2Exception} 的序列化类
 * @author: Sinda
 * @create: 2020-01-01 19:13
 */
public class FdpOAuth2ExceptionJacksonSerializer extends StdSerializer<FdpOAuth2Exception> {

    @Override
    public void serialize(FdpOAuth2Exception e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("code", CommonContent.REQUEST_FAIL);
        jsonGenerator.writeStringField("msg", e.getMessage());
        jsonGenerator.writeStringField("data", e.getCode());
        jsonGenerator.writeEndObject();
    }

    public FdpOAuth2ExceptionJacksonSerializer() {
        super(FdpOAuth2Exception.class);
    }
}
