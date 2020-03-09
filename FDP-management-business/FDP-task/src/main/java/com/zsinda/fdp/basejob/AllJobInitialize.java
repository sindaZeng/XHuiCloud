package com.zsinda.fdp.basejob;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.zsinda.fdp.annotation.EnableElasticJob;

import java.util.Map;

/**
 * @program: FDPlatform
 * @description: SimpleJobConfiguration
 * @author: Sinda
 * @create: 2020-03-06 11:16
 */
public class AllJobInitialize extends BaseJobInitialize {

    /**
     * 初始化所有任务
     */
    public void init() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EnableElasticJob.class);
        for (Map.Entry<String, Object> bean : beans.entrySet()) {
            Object instance = bean.getValue();
            Class<?> instanceClass = instance.getClass();
            EnableElasticJob annotation = instanceClass.getAnnotation(EnableElasticJob.class);
            Class<?>[] interfaces = instanceClass.getInterfaces();
            checkJobType(interfaces,annotation.jobType(),instanceClass);
            initJob(annotation, (ElasticJob) instance, annotation.jobType());
        }
    }


}
