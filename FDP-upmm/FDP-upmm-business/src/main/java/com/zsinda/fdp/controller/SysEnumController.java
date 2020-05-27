package com.zsinda.fdp.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.zsinda.fdp.enums.tantent.TantentStateEnum;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
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
 * @program: FDPlatform
 * @description: 获取系统枚举统一入口
 * @author: Sinda
 * @create: 2020-05-27 18:08
 */
@RestController
@RequestMapping("/enum")
@AllArgsConstructor
@Api(value = "enum", tags = "系统枚举管理模块")
public class SysEnumController {

    private static Map<String, Map<Integer, String>> allEnums = Maps.newHashMap();

    static {
        allEnums.put(TantentStateEnum.class.getSimpleName(),
                uniqueIndex(Arrays.asList(TantentStateEnum.values()), TantentStateEnum::getType, TantentStateEnum::getDescription));
    }

    @GetMapping("/{name}")
    public R<Map<Integer, String>> getEnums(@PathVariable String name) {
        if (allEnums.containsKey(name)) {
            R.ok(allEnums.get(name));
        }
        return R.failed("无此枚举!");
    }


    public static <K, V, M> ImmutableMap<K, M> uniqueIndex(Iterable<V> values, Function<? super V, K> keyFunction, Function<? super V, M> valueFunction) {
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
