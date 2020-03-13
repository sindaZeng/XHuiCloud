//package com.zsinda.fdp.job;
//
//import com.dangdang.ddframe.job.api.ShardingContext;
//import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @program: FDPlatform
// * @description: 流式任务
// * @author: Sinda
// * @create: 2020-03-08 19:44
// */
////@EnableElasticJob(cron = "0/10 * * * * ?", shardingTotalCount = 2, isStreamProcess = true, jobType = JobType.DATAFLOW)
//public class TestDataflowJob implements DataflowJob<Integer> {
//    @Override
//    public List<Integer> fetchData(ShardingContext shardingContext) {
//        List<Integer> list = new ArrayList<>();
////        for (int i = 0; i < 1; i++) {
//            list.add(1);
//            System.out.println("添加了");
////        }
//        return list;
//    }
//
//    @Override
//    public void processData(ShardingContext shardingContext, List<Integer> data) {
//            System.out.println("移除了"+data);
//    }
//}
