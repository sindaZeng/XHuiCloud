package com.xhuicloud.common.datasource.source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * @program: XHuiCloud
 * @description: 默认数据源配置
 * @author: Sinda
 * @create: 2020-01-29 15:30
 */
@Slf4j
@ConditionalOnMissingBean(DynamicDataSourceConfig.class)
public class DefaultDataSourceConfig {
}

