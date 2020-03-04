package com.zsinda.fdp.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.zsinda.fdp.constant.CommonConstants;
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
    public void serialize(FdpOAuth2Exception e, JsonGenerator generator, SerializerProvider s) throws IOException {
        generator.writeStartObject();
        generator.writeObjectField("code", CommonConstants.REQUEST_FAIL);
        generator.writeStringField("msg", e.getMessage());
        generator.writeStringField("data", e.getErrorCode());
        generator.writeEndObject();
    }

    public FdpOAuth2ExceptionJacksonSerializer() {
        super(FdpOAuth2Exception.class);
    }
}
