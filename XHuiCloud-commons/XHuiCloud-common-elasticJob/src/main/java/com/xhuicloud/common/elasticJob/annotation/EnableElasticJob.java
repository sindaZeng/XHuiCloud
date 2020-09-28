package com.xhuicloud.common.elasticJob.annotation;

import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: XHuiCloud
 * @description: 开启一个任务
 * @author: Sinda
 * @create: 2020-03-06 11:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface EnableElasticJob {

    /**
     * 任务类型:简单任务，流式任务，脚本任务。
     * 默认: 简单任务
     */
    JobType jobType() default JobType.SIMPLE;

    /**
     * 任务名称
     * 默认: 类名
     */
    String jobName() default "";

    /**
     * 表达式
     */
    String cron() default "";

    /**
     * 设置作业描述信息
     */
    String description() default "";

    /**
     * 设置作业自定义参数.
     * 可以配置多个相同的作业, 但是用不同的参数作为不同的调度实例.
     */
    String jobParameter() default "";

    /**
     * 扩展异常处理类
     */
    String jobExceptionHandler() default "";

    /**
     * 扩展作业处理线程池类
     */
    String executorServiceHandler() default "";

    /**
     * 分片总数
     * 默认: 1
     */
    int shardingTotalCount() default 1;

    /**
     * 设置分片序列号和个性化参数对照表.
     * 分片序列号和参数用等号分隔, 多个键值对用逗号分隔. 类似map.
     * 分片序列号从0开始, 不可大于或等于作业分片总数.
     * 如:
     * 0=a,1=b,2=c
     */
    String shardingItemParameters() default "";

    /**
     * 允许客户端配置覆盖注册中心
     * 默认: true
     */
    boolean overwrite() default true;

    /**
     * 是否流式处理数据
     * 默认: false
     */
    boolean isStreamProcess() default false;

    /**
     * 设置是否开启失效转移
     * 只有对monitorExecution的情况下才可以开启失效转移
     * 默认: true
     */
    boolean isFailover() default true;

    /**
     * 是否开启错过任务重新执行
     * 默认: true
     */
    boolean misfire() default true;

    /**
     * 自定义分片策略
     * 默认: 根据作业名的哈希值奇偶数决定IP升降序算法的分片策略
     */
    Class<? extends JobShardingStrategy> strategy() default AverageAllocationJobShardingStrategy.class;

    /**
     * 自定义监听器
     * 默认: 空
     */
    Class<? extends ElasticJobListener>[] listeners() default {};
}
