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

package com.xhuicloud.common.xxl.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "job")
public class XxlJobProperties {

    /**
     * xxl-job调度中心部署地址
     * 如集群部署多个地址则用逗号隔开。
     * 执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
     */
    private String addresses = "http://127.0.0.1:8080/xxl-job-admin";

    /**
     * 执行器配置
     */
    private XxlExecutorProperties executor = new XxlExecutorProperties();

    @Data
    @NoArgsConstructor
    public static class XxlExecutorProperties {

        /**
         * 执行器AppName
         * 执行器心跳注册分组依据；
         * 为空则关闭自动注册
         */
        private String appName;

        /**
         * 服务注册地址,优先使用该配置作为注册地址
         * 为空时使用内嵌服务 ”IP:PORT“
         * 作为注册地址 从而更灵活的支持容器类型执行器动态IP和动态映射端口问题
         */
        private String address;

        /**
         * 执行器IP
         * 默认为空表示自动获取IP，多网卡时可手动设置指定IP ，
         * 该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"
         */
        private String ip;

        /**
         * 执行器端口号 小于等于0则自动获取；
         * 默认端口为9999
         * 单机部署多个执行器时，注意要配置不同执行器端口；
         */
        private Integer port = 0;

        /**
         * 执行器通讯TOKEN
         * 非空时启用；
         */
        private String accessToken = "XXXXXXXX";

        /**
         * 执行器运行日志文件存储磁盘路径
         * 需要对该路径拥有读写权限；为空则使用默认路径；
         */
        private String logPath;

        /**
         * 执行器日志保存天数
         * 值大于3时生效，启用执行器Log文件定期清理功能，否则不生效；
         */
        private Integer logRetentionDays = 30;
    }

}
