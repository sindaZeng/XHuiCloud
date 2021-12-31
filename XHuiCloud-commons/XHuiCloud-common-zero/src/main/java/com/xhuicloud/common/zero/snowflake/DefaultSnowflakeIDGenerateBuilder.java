/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

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
