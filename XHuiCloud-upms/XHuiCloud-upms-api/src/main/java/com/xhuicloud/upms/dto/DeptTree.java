package com.xhuicloud.upms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: XHuiCloud
 * @description: DeptTree
 * @author: Sinda
 * @create: 2020-03-21 15:57
 */
@Data
@ApiModel(value = "部门树")
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends TreeNode {

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value="区域、地址、工位")
    private String address;
}
