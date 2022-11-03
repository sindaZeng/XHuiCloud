package com.xhuicloud.upms.vo;

import lombok.Data;

import java.util.List;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/11/1
 */
@Data
public class PredicateAndFilterVo {

    private List<PredicateVo> predicateVos;

    private List<FilterVo> filterVos;
}
