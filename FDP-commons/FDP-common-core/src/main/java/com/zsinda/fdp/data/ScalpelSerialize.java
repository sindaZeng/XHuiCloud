package com.zsinda.fdp.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.zsinda.fdp.utils.ScalpelUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * @program: FDPlatform
 * @description: ScalpelSerialize
 * @author: Sinda
 * @create: 2020-05-29 17:15
 */
@AllArgsConstructor
@NoArgsConstructor
public class ScalpelSerialize extends JsonSerializer<String> implements ContextualSerializer {

    private ScalpelTypeEnum type;

    private String domain;

    private Scalpel scalpel;

    @Override
    public void serialize(String source, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        switch (type) {
            case MOSAIC:
                jsonGenerator.writeString(ScalpelUtil.mosaic(source,scalpel));
                break;
            case ADD_DOMAIN:
                jsonGenerator.writeString(ScalpelUtil.addDomain(source, domain));
                break;
            default:
                throw new IllegalArgumentException("Unknow Scalpel type " + type);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                Scalpel scalpel = beanProperty.getAnnotation(Scalpel.class);
                if (scalpel == null) {
                    scalpel = beanProperty.getContextAnnotation(Scalpel.class);
                }
                if (scalpel != null) {
                    return new ScalpelSerialize(scalpel.type(),scalpel.domain(),scalpel);
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}
