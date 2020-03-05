package com.zsinda.fdp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @program: FDPlatform
 * @description: SnowflakeIDGenerateImpl
 * @author: Sinda
 * @create: 2020-03-04 17:46
 */
@Data
@Component
@ConfigurationProperties(prefix = "fdp.zero.snowflake")
public class DefaultSnowflakeProperties  {

	private String zkAddress;

	private Integer port;

}
