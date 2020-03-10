package com.zsinda.fdp.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

/**
 * @program: FDPlatform
 * @description: CustomListener
 * @author: Sinda
 * @create: 2020-03-10 14:14
 */
public class CustomListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        System.out.println("任务执行前");
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        System.out.println("任务执行后");
    }
}
