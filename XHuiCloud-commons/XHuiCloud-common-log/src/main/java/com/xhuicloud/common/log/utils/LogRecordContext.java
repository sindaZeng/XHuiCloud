package com.xhuicloud.common.log.utils;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc 日志上下文：每执行一个方法都会压栈一个 Map，方法执行完之后会 Pop 掉这个 Map，从而避免变量共享和覆盖问题
 * @Author Sinda
 * @Date 2023/1/30
 */
public class LogRecordContext {

    /**
     * Map 对应了变量的名称和变量的值
     * 设置一个 Stack 结构是为了: 在使用 AuditRecord 的方法里面调用了另一个使用 AuditRecord 方法的时候，防止变量被污染
     */
    private static final TransmittableThreadLocal<Stack<Map<String, Object>>> variableMapStack = new TransmittableThreadLocal<>();

    public static Object putVariable(String key, Object value) {
        variableMapStack.get().peek().put(key, value);
        return value;
    }

    public static Map<String, Object> getVariables() {
        return peek();
    }

    public static Map<String, Object> peek() {
        return variableMapStack.get().peek();
    }

    public static void clean() {
        variableMapStack.remove();
    }

    /**
     * 创建当前方法上下文
     */
    public static void putEmptySpan() {
        push(new ConcurrentHashMap<>());
    }

    /**
     * 压栈
     */
    private static void push(Map<String, Object> map) {
        Stack<Map<String, Object>> maps = variableMapStack.get();
        if (Objects.isNull(maps)) {
            maps = new Stack<>();
            variableMapStack.set(maps);
        }
        maps.push(map);
    }

}
