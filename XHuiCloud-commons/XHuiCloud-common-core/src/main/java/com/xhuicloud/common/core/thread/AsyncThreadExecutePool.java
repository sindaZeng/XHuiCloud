package com.xhuicloud.common.core.thread;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@AllArgsConstructor
public class AsyncThreadExecutePool implements AsyncConfigurer {

    private final AsyncThreadExecuteProperties properties;

    private static final String THREAD_NAME = "xhui-async-";

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(properties.getCorePoolSize());
        //最大线程数
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        //队列容量
        executor.setQueueCapacity(properties.getQueueCapacity());
        //活跃时间
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        //线程名字前缀
        executor.setThreadNamePrefix(THREAD_NAME);
        // ThreadPoolExecutor.AbortPolicy 丢弃任务并抛出RejectedExecutionException异常(默认)。
        // ThreadPoolExecutor.DiscardPolic 丢弃任务，但是不抛出异常。
        // ThreadPoolExecutor.DiscardOldestPolicy 丢弃队列最前面的任务，然后重新尝试执行任务
        // ThreadPoolExecutor.CallerRunsPolic 由调用线程处理该任务
        ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        executor.setRejectedExecutionHandler(callerRunsPolicy);
        executor.initialize();
        log.info("线程池初始化完成: corePoolSize = {}, maxPoolSize = {}, " +
                "queueCapacity = {}, keepAliveSeconds = {}, rejectedExecutionHandler = {}", properties.getCorePoolSize(),
                properties.getMaxPoolSize(), properties.getQueueCapacity(), properties.getKeepAliveSeconds(), callerRunsPolicy.getClass());
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("========= thread pool exception =========");
            log.error("========={}========={}", throwable.getMessage(), throwable);
            log.error("exception method:{}" + method.getName());
        };
    }
}
