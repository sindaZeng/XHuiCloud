package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志
 */
@Data
@TableName(value = "sys_log")
@ApiModel(value = "com-zsinda-fdp-entity-SysLog")
public class SysLog implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 操作IP
     */
    @TableField(value = "request_ip")
    @ApiModelProperty(value = "操作IP")
    private String requestIp;

    /**
     * 日志类型
     * #LogType{0:操作类型;1:异常类型}
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "日志类型,#LogType{0:操作类型;1:异常类型}")
    private String type;

    /**
     * 操作人
     */
    @TableField(value = "user_name")
    @ApiModelProperty(value = "操作人")
    private String userName;

    /**
     * 操作描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value = "操作描述")
    private String description;

    /**
     * 类路径
     */
    @TableField(value = "class_path")
    @ApiModelProperty(value = "类路径")
    private String classPath;

    /**
     * 请求方法
     */
    @TableField(value = "request_method")
    @ApiModelProperty(value = "请求方法")
    private String requestMethod;

    /**
     * 请求地址
     */
    @TableField(value = "request_uri")
    @ApiModelProperty(value = "请求地址")
    private String requestUri;

    /**
     * 请求类型 {GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}
     */
    @TableField(value = "http_method")
    @ApiModelProperty(value = "请求类型 {GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}")
    private String httpMethod;

    /**
     * 请求参数
     */
    @TableField(value = "params")
    @ApiModelProperty(value = "请求参数")
    private String params;

    /**
     * 返回值
     */
    @TableField(value = "result")
    @ApiModelProperty(value = "返回值")
    private String result;

    /**
     * 异常详情信息
     */
    @TableField(value = "ex_desc")
    @ApiModelProperty(value = "异常详情信息")
    private String exDesc;

    /**
     * 异常描述
     */
    @TableField(value = "ex_detail")
    @ApiModelProperty(value = "异常描述")
    private String exDetail;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 结束时间
     */
    @TableField(value = "finish_time")
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime finishTime;

    /**
     * 执行时间
     */
    @TableField(value = "time")
    @ApiModelProperty(value = "执行时间")
    private String time;

    /**
     * 浏览器
     */
    @TableField(value = "user_agent")
    @ApiModelProperty(value = "浏览器")
    private String userAgent;

    /**
     * 0: 禁用 1：启用
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "0: 禁用 1：启用")
    private String delFlag;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    private static final long serialVersionUID = 1L;
}
