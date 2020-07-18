package com.zsinda.fdp.rule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.zsinda.fdp.constant.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.Map;

/**
 * @program: FDPlatform
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
        //TODO 如果没有携带版本,自定义自己的规则。此处是写明，可用实例中随机挑选 ——Sinda
        return reachableServers.get(RandomUtil.randomInt(reachableServers.size()));
    }
}
