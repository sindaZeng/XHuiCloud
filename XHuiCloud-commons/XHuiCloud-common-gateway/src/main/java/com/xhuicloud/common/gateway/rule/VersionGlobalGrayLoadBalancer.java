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

package com.xhuicloud.common.gateway.rule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.xhuicloud.common.core.constant.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.Map;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/7/18 10:40 下午
 */
@Slf4j
@AllArgsConstructor
public class VersionGlobalGrayLoadBalancer implements GlobalGrayLoadBalancer {

    private final DiscoveryClient discoveryClient;

    @Override
    public ServiceInstance choose(String serviceId, ServerHttpRequest request) {

        List<ServiceInstance> reachableServers = discoveryClient.getInstances(serviceId);

        // 匹配成功实例
        List<ServiceInstance> matchSuccessfulServers;

        if (CollUtil.isEmpty(reachableServers)) {
            log.warn("注册中心没有任何实例可用! {}", serviceId);
            return null;
        }

        // 获取请求version，无则随机返回可用实例
        String reqVersion = request.getHeaders().getFirst(CommonConstants.VERSION);

        if (StringUtils.isNotBlank(reqVersion)) {
            matchSuccessfulServers = Lists.newArrayList();
            // 遍历可以实例元数据，若匹配则返回此实例
            for (ServiceInstance instance : reachableServers) {
                Map<String, String> metadata = instance.getMetadata();
                String targetVersion = MapUtil.getStr(metadata, CommonConstants.VERSION);
                if (reqVersion.equalsIgnoreCase(targetVersion)) {
                    log.debug("版本匹配成功 :{} {}", reqVersion, instance);
                    matchSuccessfulServers.add(instance);
                }
            }

            if (CollUtil.isNotEmpty(matchSuccessfulServers)) {
                return matchSuccessfulServers.get(RandomUtil.randomInt(matchSuccessfulServers.size()));
            }

        }
        return reachableServers.get(RandomUtil.randomInt(reachableServers.size()));
    }
}
