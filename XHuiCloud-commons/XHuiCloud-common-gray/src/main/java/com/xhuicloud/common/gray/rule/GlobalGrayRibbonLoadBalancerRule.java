package com.xhuicloud.common.gray.rule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import com.xhuicloud.common.core.constant.CommonConstants;
import com.xhuicloud.common.core.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: XHuiCloud
 * @description:
 * @author: Sinda
 * @create: 2020/7/18 9:46 下午
 */
@Slf4j
public class GlobalGrayRibbonLoadBalancerRule extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }

    @Override
    public Server choose(Object o) {

        // 可用实例
        List<Server> reachableServers = getLoadBalancer().getReachableServers();

        // 匹配成功实例
        List<Server> matchSuccessfulServers;

        if (CollUtil.isEmpty(reachableServers)) {
            log.warn("注册中心没有任何实例可用! {}", o);
            return null;
        }

        // 获取请求version，无则随机返回可用实例
        String reqVersion = WebUtils.getRequest() != null ? WebUtils.getRequest().getHeader(CommonConstants.VERSION)
                : null;

        if (StringUtils.isNotBlank(reqVersion)) {
            matchSuccessfulServers = new ArrayList();
            // 遍历可以实例元数据，若匹配则返回此实例
            for (Server server : reachableServers) {
                NacosServer nacosServer = (NacosServer) server;
                Map<String, String> metadata = nacosServer.getMetadata();
                String targetVersion = MapUtil.getStr(metadata, CommonConstants.VERSION);
                if (reqVersion.equalsIgnoreCase(targetVersion)) {
                    log.debug("版本匹配成功 :{} {}", reqVersion, nacosServer);
                    matchSuccessfulServers.add(nacosServer);
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
