package com.xhuicloud.common.zero.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @program: XHuiCloud
 * @description: SnowflakeIDGenerateImpl
 * @author: Sinda
 * @create: 2020-03-04 17:46
 */
@Data
@ConfigurationProperties(prefix = "xhuicloud.zero.snowflake")
public class DefaultSnowflakeProperties  {

	private String zkAddress;

	private Integer port;

}
