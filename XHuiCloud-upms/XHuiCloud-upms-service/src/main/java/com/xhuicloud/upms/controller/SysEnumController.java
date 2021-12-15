package com.xhuicloud.upms.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.xhuicloud.common.core.enums.base.ParamTypeEnum;
import com.xhuicloud.common.core.enums.base.AuditStateEnum;
import com.xhuicloud.common.core.utils.Response;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @program: XHuiCloud
 * @description: 获取系统枚举统一入口
 * @author: Sinda
 * @create: 2020-05-27 18:08
 */
@RestController
@RequestMapping("/enum")
@Api(value = "enum", tags = "系统枚举管理模块")
public class SysEnumController {

    private static Map<String, Map<Integer, String>> allEnums = Maps.newHashMap();

    static {
        allEnums.put(AuditStateEnum.class.getSimpleName(),
                uniqueIndex(Arrays.asList(AuditStateEnum.values()), AuditStateEnum::getType, AuditStateEnum::getDescription));
        allEnums.put(ParamTypeEnum.class.getSimpleName(),
                uniqueIndex(Arrays.asList(ParamTypeEnum.values()), ParamTypeEnum::getType, ParamTypeEnum::getDescription));
    }

    @GetMapping("/{name}")
    public Response<Map<Integer, String>> getEnums(@PathVariable String name) {
        if (allEnums.containsKey(name)) {
            return Response.success(allEnums.get(name));
        }
        return Response.failed("无此枚举!请检查枚举名称!");
    }


    public static <K, V, M> ImmutableMap<K, M> uniqueIndex(Iterable<V> values,
                                                           Function<? super V, K> keyFunction,
                                                           Function<? super V, M> valueFunction) {
        Iterator<V> iterator = values.iterator();
        checkNotNull(keyFunction);
        checkNotNull(valueFunction);
        ImmutableMap.Builder<K, M> builder = ImmutableMap.builder();
        while (iterator.hasNext()) {
            V value = iterator.next();
            builder.put(keyFunction.apply(value), valueFunction.apply(value));
        }
        try {
            return builder.build();
        } catch (IllegalArgumentException duplicateKeys) {
            throw new IllegalArgumentException(
                    duplicateKeys.getMessage()
                            + ".若要在键下索引多个值，请使用: Multimaps.index.");
        }
    }

}
