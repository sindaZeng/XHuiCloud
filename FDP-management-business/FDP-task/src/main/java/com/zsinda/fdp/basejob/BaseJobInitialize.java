package com.zsinda.fdp.basejob;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.exception.JobConfigurationException;
import com.dangdang.ddframe.job.executor.handler.JobProperties;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.zsinda.fdp.annotation.EnableElasticJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

/**
 * @program: FDPlatform
 * @description: BaseJobInitialize
 * @author: Sinda
 * @create: 2020-03-06 16:04
 */
@Slf4j
public abstract class BaseJobInitialize {

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected CoordinatorRegistryCenter coordinatorRegistryCenter;

    @Autowired(required = false)
    protected DataSource dataSource;

    public abstract void init();

    protected void initJob(EnableElasticJob annotation, ElasticJob elasticJob, JobType jobType) {
        String jobName = getJobName(annotation, elasticJob);
        // 核心配置
        JobCoreConfiguration jobCoreConfiguration = getJobCoreConfiguration(annotation, jobName);
        // 定时任务 类型配置
        JobTypeConfiguration jobTypeConfiguration = getJobTypeConfiguration(annotation, elasticJob, jobCoreConfiguration, jobType);
        // 根配置
        LiteJobConfiguration liteJobConfiguration = getLiteJobConfiguration(annotation, jobTypeConfiguration);
        if (dataSource ==null){
            new SpringJobScheduler(elasticJob, coordinatorRegistryCenter, liteJobConfiguration).init();
        }else {
            // 数据源
            JobEventRdbConfiguration jobEventRdbConfiguration = new JobEventRdbConfiguration(dataSource);
            new SpringJobScheduler(elasticJob, coordinatorRegistryCenter, liteJobConfiguration,jobEventRdbConfiguration).init();
        }
     }

    /**
     * 获取任务类型
     *
     * @param annotation
     * @param elasticJob
     * @param jobCoreConfiguration
     * @return
     */
    private JobTypeConfiguration getJobTypeConfiguration(EnableElasticJob annotation, ElasticJob elasticJob, JobCoreConfiguration jobCoreConfiguration, JobType jobType) {
        switch (jobType) {
            case SIMPLE:
                return new SimpleJobConfiguration(jobCoreConfiguration, elasticJob.getClass().getCanonicalName());
            case DATAFLOW:
                return new DataflowJobConfiguration(jobCoreConfiguration,
                        elasticJob.getClass().getCanonicalName(), annotation.isStreamProcess());
            default:
                return null;
        }
    }

    /**
     * 作业 根配置
     *
     * @param annotation
     * @param jobTypeConfiguration
     * @return
     */
    private LiteJobConfiguration getLiteJobConfiguration(EnableElasticJob annotation, JobTypeConfiguration jobTypeConfiguration) {
        return LiteJobConfiguration.newBuilder(jobTypeConfiguration)
                .jobShardingStrategyClass(annotation.Strategy().getCanonicalName())
                .overwrite(annotation.overwrite()).build();
    }

    /**
     * 作业 核心配置
     *
     * @param annotation
     * @param jobName
     * @return
     */
    private JobCoreConfiguration getJobCoreConfiguration(EnableElasticJob annotation, String jobName) {
        JobCoreConfiguration.Builder builder = JobCoreConfiguration.newBuilder(jobName,
                annotation.cron(), annotation.shardingTotalCount()).description(annotation.description())
                .failover(annotation.isFailover()).jobParameter(annotation.jobParameter()).misfire(annotation.misfire())
                .shardingItemParameters(annotation.shardingItemParameters());
        if (StringUtils.isNotBlank(annotation.jobExceptionHandler())) {
            builder.jobProperties(JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(), annotation.jobExceptionHandler());
        }
        if (StringUtils.isNotBlank(annotation.executorServiceHandler())) {
            builder.jobProperties(JobProperties.JobPropertiesEnum.EXECUTOR_SERVICE_HANDLER.getKey(), annotation.executorServiceHandler());
        }
        return builder.build();
    }

    /**
     * 获取任务名称
     *
     * @param annotation
     * @param elasticJob
     * @return
     */
    private String getJobName(EnableElasticJob annotation, ElasticJob elasticJob) {
        return StringUtils.isEmpty(annotation.jobName()) ? elasticJob.getClass().getName() : annotation.jobName();
    }

    /**
     * 根据任务类型,校验是否为此类型的所需的实现类
     * @param interfaces
     * @param jobType
     * @param instanceClass
     */
    protected void checkJobType(Class<?>[] interfaces, JobType jobType, Class<?> instanceClass) {
        List<Class<?>> classes = Arrays.asList(interfaces);
        switch (jobType) {
                case SIMPLE:
                    if (!classes.contains(SimpleJob.class)){
                        throw new JobConfigurationException("Wrong implementation type! In this "
                                +instanceClass+" ! And The right way implementation SimpleJob.class");
                    }
                    break;
                case DATAFLOW:
                    if (!classes.contains(DataflowJob.class)){
                        throw new JobConfigurationException("Wrong implementation type! In this"
                                +instanceClass+" ! And The right way implementation DataflowJob.class");
                    }
                    break;
                default:
                    throw new JobConfigurationException("Wrong implementation type! In this"+instanceClass);
            }
    }
}
