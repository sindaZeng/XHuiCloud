package com.xhuicloud.common.lock.strategy;

import com.xhuicloud.common.lock.properties.XHuiRedisProperties;
import org.redisson.config.Config;

/**
 * @program: XHuiCloud
 * @description: Redisson配置构建接口
 * @author: Sinda
 * @create: 2020-02-04 10:51
 */
public interface ConfigService {

    Config createConfig(XHuiRedisProperties XHuiRedisProperties);
    
}
