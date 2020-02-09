package com.zsinda.fdp.strategy;

import com.zsinda.fdp.properties.FdpRedisProperties;
import org.redisson.config.Config;

/**
 * @program: FDPlatform
 * @description: Redisson配置构建接口
 * @author: Sinda
 * @create: 2020-02-04 10:51
 */
public interface ConfigService {

    Config createConfig(FdpRedisProperties fdpRedisProperties);
}
