package com.xhuicloud.upms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="用户部门")
public class SysUserDept implements Serializable {

    @ApiModelProperty(value="用户Id")
    private Integer userId;

    @ApiModelProperty(value="部门Id")
    private Integer deptId;

    private static final long serialVersionUID = 1L;
}
