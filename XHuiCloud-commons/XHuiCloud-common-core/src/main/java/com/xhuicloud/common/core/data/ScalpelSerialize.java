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

package com.xhuicloud.common.core.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.xhuicloud.common.core.annotation.Scalpel;
import com.xhuicloud.common.core.utils.ScalpelUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * @program: XHuiCloud
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
