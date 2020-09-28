package com.xhuicloud.common.zero.snowflake;

import com.xhuicloud.common.zero.base.IDGenerate;
import com.xhuicloud.common.zero.base.IDGenerateBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: XHuiCloud
 * @description: SnowflakeIDGenerateImpl
 * @author: Sinda
 * @create: 2020-03-04 17:46
 */
@Slf4j
public class DefaultSnowflakeIDGenerateBuilder implements IDGenerateBuilder {

    private String zkAddress= "127.0.0.1";

    private Integer port = 8080;

    public static DefaultSnowflakeIDGenerateBuilder create() {
        return new DefaultSnowflakeIDGenerateBuilder();
    }

    @Override
    public IDGenerate build() {
        DefaultSnowflakeIDGenerate defaultSnowflakeIDGenerate = new DefaultSnowflakeIDGenerate(zkAddress,port);
        return defaultSnowflakeIDGenerate;
    }

    public DefaultSnowflakeIDGenerateBuilder zkAddress(String zkAddress) {
        this.zkAddress=zkAddress;
        return this;
    }

    public DefaultSnowflakeIDGenerateBuilder port(Integer port) {
        this.port=port;
        return this;
    }

}
