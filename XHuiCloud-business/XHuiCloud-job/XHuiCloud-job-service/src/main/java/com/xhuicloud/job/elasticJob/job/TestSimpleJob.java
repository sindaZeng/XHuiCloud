package com.xhuicloud.job.elasticJob.job;//package com.zsinda.xhuicloud.job;
//
//import com.dangdang.ddframe.job.api.JobType;
//import com.dangdang.ddframe.job.api.ShardingContext;
//import com.dangdang.ddframe.job.api.simple.SimpleJob;
//
///**
// * @program: XHuiCloud
// * @description: TestSimpleJob
// * @author: Sinda
// * @create: 2020-03-06 11:11
// */
//@EnableElasticJob(cron = "0/10 * * * * ?", shardingTotalCount = 2, jobType = JobType.SIMPLE,listeners = CustomListener.class)
//public class TestSimpleJob implements SimpleJob{
//
//    @Override
//    public void execute(ShardingContext shardingContext) {
//        System.out.println("我是分片项:" + shardingContext.getShardingItem());
//    }
//}
