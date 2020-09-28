package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 路由表
 */
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

    /**
     * 路由地址
     */
    @ApiModelProperty(value="路由地址")
    private String uri;

    /**
     * 排序
     */
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

    /**
     * 0:否 1:是
     */
    @ApiModelProperty(value="0:否 1:是")
    private Integer isDel;

}
