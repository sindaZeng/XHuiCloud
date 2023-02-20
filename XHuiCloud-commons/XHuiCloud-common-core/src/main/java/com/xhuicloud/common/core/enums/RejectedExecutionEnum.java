package com.xhuicloud.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/20
 */
@Getter
@AllArgsConstructor
public enum RejectedExecutionEnum {

    AbortPolicy(new ThreadPoolExecutor.AbortPolicy(), "丢弃任务并抛出RejectedExecutionException异常(默认)"),

    DiscardPolicy(new ThreadPoolExecutor.DiscardPolicy(), "丢弃任务，但是不抛出异常"),

    DiscardOldestPolicy(new ThreadPoolExecutor.DiscardOldestPolicy(), "丢弃队列最前面的任务，然后重新尝试执行任务"),

    CallerRunsPolicy(new ThreadPoolExecutor.CallerRunsPolicy(), "由调用线程处理该任务");

    private final RejectedExecutionHandler handler;

    private final String description;
}
