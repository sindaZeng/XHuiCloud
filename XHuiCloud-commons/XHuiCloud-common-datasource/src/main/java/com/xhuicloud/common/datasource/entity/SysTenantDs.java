package com.xhuicloud.common.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "租户、模块数据源")
public class SysTenantDs {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 0:master 1:slave
     */
    @ApiModelProperty(value = "0:master 1:slave")
    private Integer num;

    /**
     * 数据源名称
     */
    @ApiModelProperty(value = "数据源名称")
    private String name;

    /**
     * jdbcurl
     */
    @ApiModelProperty(value = "上级ID")
    private String url;

    /**
     * type
     */
    @ApiModelProperty(value = "type")
    private String type;

    /**
     * username
     */
    @ApiModelProperty(value = "username")
    private String username;

    /**
     * password
     */
    @ApiModelProperty(value = "password")
    private String password;

    /**
     * ds_name
     */
    @ApiModelProperty(value = "ds_name")
    private String dsName;

    /**
     * host
     */
    @ApiModelProperty(value = "host")
    private String host;

    /**
     * port
     */
    @ApiModelProperty(value = "port")
    private String port;

    /**
     * tenantId
     */
    @ApiModelProperty(value = "tenantId")
    private Integer tenantId;

    /**
     * module
     */
    @ApiModelProperty(value = "module")
    private String module;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    private LocalDateTime updateTime;

    /**
     * 0:否 1:是
     */
    @ApiModelProperty(value = "0:否 1:是")
    private Integer isDel;

    private static final long serialVersionUID = 1L;
}
