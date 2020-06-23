package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value="路由")
public class SysRouteConf implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 路由名称
     */
    @ApiModelProperty(value="路由名称")
    private String routeName;

    /**
     * 路由id
     */
    @ApiModelProperty(value="路由id")
    private String routeId;

    /**
     * 谓词/断言
     */
    @ApiModelProperty(value="谓词/断言")
    private String predicates;

    /**
     * 过滤器
     */
    @ApiModelProperty(value="过滤器")
    private String filters;

    @ApiModelProperty(value="")
    private String uri;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;

    private String delFlag;

}
