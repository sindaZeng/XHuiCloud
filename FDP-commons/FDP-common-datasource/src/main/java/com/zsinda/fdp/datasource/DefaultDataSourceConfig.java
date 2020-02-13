package com.zsinda.fdp.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * @program: FDPlatform
 * @description: 默认数据源配置
 * @author: Sinda
 * @create: 2020-01-29 15:30
 */
@Slf4j
@ConditionalOnMissingBean(DynamicDataSourceConfig.class)
public class DefaultDataSourceConfig {




}

