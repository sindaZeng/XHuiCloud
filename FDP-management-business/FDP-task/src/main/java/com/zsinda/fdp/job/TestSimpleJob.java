package com.zsinda.fdp.job;

import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zsinda.fdp.annotation.EnableElasticJob;

/**
 * @program: FDPlatform
 * @description: TestSimpleJob
 * @author: Sinda
 * @create: 2020-03-06 11:11
 */
@EnableElasticJob(cron = "0/10 * * * * ?", shardingTotalCount = 2, jobType = JobType.DATAFLOW)
public class TestSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("我是分片项:" + shardingContext.getShardingItem());
    }
}
