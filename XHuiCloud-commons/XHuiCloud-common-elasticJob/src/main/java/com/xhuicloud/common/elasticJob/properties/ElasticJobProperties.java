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

package com.xhuicloud.common.elasticJob.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: XHuiCloud
 * @description: ElasticJobProperties
 * @author: Sinda
 * @create: 2020-03-05 23:20
 */
@Data
@ConfigurationProperties(prefix = "xhuicloud.elasticjob")
public class ElasticJobProperties {

    /**
     * 注册中心
     */
    private ZkConfiguration zookeeper;

    @Data
    public static class ZkConfiguration {

        /**
         * 连接Zookeeper服务器的列表.
         * 包括IP地址和端口号.
         * 多个地址用逗号分隔.
         * 如: host1:2181,host2:2181
         */
        private String serverLists = "localhost:2181";

        /**
         * 命名空间.
         */
        private String namespace;

        /**
         * 等待重试的间隔时间的初始值.
         * 单位毫秒.
         */
        private int baseSleepTimeMilliseconds = 100000;

        /**
         * 等待重试的间隔时间的最大值.
         * 单位毫秒.
         */
        private int maxSleepTimeMilliseconds = 30000;

        /**
         * 最大重试次数.
         */
        private int maxRetries = 3;

        /**
         * 会话超时时间.
         * 单位毫秒.
         */
        private int sessionTimeoutMilliseconds=10000;

        /**
         * 连接超时时间.
         * 单位毫秒.
         */
        private int connectionTimeoutMilliseconds=10000;

        /**
         * 连接Zookeeper的权限令牌.
         * 缺省为不需要权限验证.
         */
        private String digest;
    }


}
