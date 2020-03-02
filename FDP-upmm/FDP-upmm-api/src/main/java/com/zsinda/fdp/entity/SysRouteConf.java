package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com-zsinda-fdp-entity-SysRouteConf")
@Data
@TableName(value = "sys_route_conf")
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
    @TableField(value = "route_name")
    @ApiModelProperty(value="路由名称")
    private String routeName;

    /**
     * 路由id
     */
    @TableField(value = "route_id")
    @ApiModelProperty(value="路由id")
    private String routeId;

    /**
     * 谓词/断言
     */
    @TableField(value = "predicates")
    @ApiModelProperty(value="谓词/断言")
    private String predicates;

    /**
     * 过滤器
     */
    @TableField(value = "filters")
    @ApiModelProperty(value="过滤器")
    private String filters;

    @TableField(value = "uri")
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
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    @TableField(value = "del_flag")
    @ApiModelProperty(value="")
    private String delFlag;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ROUTE_NAME = "route_name";

    public static final String COL_ROUTE_ID = "route_id";

    public static final String COL_PREDICATES = "predicates";

    public static final String COL_FILTERS = "filters";

    public static final String COL_URI = "uri";

    public static final String COL_SORT = "sort";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_DEL_FLAG = "del_flag";
}