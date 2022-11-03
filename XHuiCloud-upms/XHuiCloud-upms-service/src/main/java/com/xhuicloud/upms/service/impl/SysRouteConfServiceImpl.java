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

package com.xhuicloud.upms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.xhuicloud.common.gateway.support.DynamicRouteInitEvent;
import com.xhuicloud.upms.dto.RouteConfDto;
import com.xhuicloud.upms.entity.SysRouteConf;
import com.xhuicloud.upms.mapper.SysRouteConfMapper;
import com.xhuicloud.upms.service.SysRouteConfService;
import com.xhuicloud.upms.vo.FilterVo;
import com.xhuicloud.upms.vo.PredicateVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysRouteConfServiceImpl extends ServiceImpl<SysRouteConfMapper, SysRouteConf> implements SysRouteConfService {

    @Override
    public List<SysRouteConf> getRoutes() {
        return list();
    }

    @Override
    public Boolean update(SysRouteConf sysRouteConf, RouteConfDto routeConfDto) {
        sysRouteConf.setPredicates(null);
        List<PredicateVo> predicateVos = routeConfDto.getPredicateVos();
        if (CollectionUtil.isNotEmpty(predicateVos)) {
            List<PredicateDefinition> predicates = getPredicateDefinitions(predicateVos);
            sysRouteConf.setPredicates(JSONUtil.toJsonStr(predicates));
        }

        List<FilterVo> filterVos = routeConfDto.getFilterVos();
        if (CollectionUtil.isNotEmpty(filterVos)) {
            List<FilterDefinition> filterDefinitions = getFilterDefinitions(filterVos);
            sysRouteConf.setFilters(JSONUtil.toJsonStr(filterDefinitions));
        }
        Boolean isSuccess = updateById(sysRouteConf);
        SpringUtil.publishEvent(new DynamicRouteInitEvent(sysRouteConf));
        return isSuccess;
    }

    @NotNull
    private List<PredicateDefinition> getPredicateDefinitions(List<PredicateVo> predicateVos) {
        List<PredicateDefinition> predicates = new ArrayList<>();
        Map<String, List<String>> predicateVosMap = predicateVos.stream().collect(Collectors.toMap(PredicateVo::getName,
                PredicateVo -> Lists.newArrayList(PredicateVo.getValue()), (List<String> oldVal, List<String> newVal) -> {
                    oldVal.addAll(newVal);
                    return oldVal;
                }));
        for (Map.Entry<String, List<String>> predicateVosMapEntrySet : predicateVosMap.entrySet()) {
            PredicateDefinition predicateDefinition = new PredicateDefinition();
            predicateDefinition.setName(predicateVosMapEntrySet.getKey());
            predicateDefinition.setArgs(getArgs(predicateVosMapEntrySet));
            predicates.add(predicateDefinition);
        }
        return predicates;
    }

    private Map<String, String> getArgs(Map.Entry<String, List<String>> mapEntrySet) {
        List<String> value = mapEntrySet.getValue();
        Map<String, String> args = new LinkedHashMap<>();
        String key = "_genkey_%s";
        for (int i = 0; i < value.size(); i++) {
            args.put(String.format(key, i), value.get(i));
        }
        return args;
    }

    @NotNull
    private List<FilterDefinition> getFilterDefinitions(List<FilterVo> filterVos) {
        List<FilterDefinition> filterDefinitions = new ArrayList<>();
        Map<String, List<String>> filterVosMap = filterVos.stream().collect(Collectors.toMap(FilterVo::getName,
                FilterVo -> Lists.newArrayList(FilterVo.getValue()), (List<String> oldVal, List<String> newVal) -> {
                    oldVal.addAll(newVal);
                    return oldVal;
                }));
        for (Map.Entry<String, List<String>> filterVosMapEntrySet : filterVosMap.entrySet()) {
            FilterDefinition filterDefinition = new FilterDefinition();
            filterDefinition.setName(filterVosMapEntrySet.getKey());
            filterDefinition.setArgs(getArgs(filterVosMapEntrySet));
            filterDefinitions.add(filterDefinition);
        }
        return filterDefinitions;
    }

}
