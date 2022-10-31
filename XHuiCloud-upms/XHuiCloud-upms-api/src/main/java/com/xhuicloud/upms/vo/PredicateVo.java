package com.xhuicloud.upms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/10/31
 */
@Data
public class PredicateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String value;

}
