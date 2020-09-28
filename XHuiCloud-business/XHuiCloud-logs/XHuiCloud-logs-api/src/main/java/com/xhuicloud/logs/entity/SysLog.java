package com.xhuicloud.logs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志
 */
@Data
@ApiModel(value = "系统日志")
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
    @ApiModelProperty(value = "操作IP")
    private String requestIp;

    /**
     * 日志类型
     * #LogType{0:操作类型;1:异常类型}
     */
    @ApiModelProperty(value = "日志类型,#LogType{0:操作类型;1:异常类型}")
    private String type;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String userName;

    /**
     * 操作描述
     */
    @ApiModelProperty(value = "操作描述")
    private String description;

    /**
     * 类路径
     */
    @ApiModelProperty(value = "类路径")
    private String classPath;

    /**
     * 请求方法
     */
    @ApiModelProperty(value = "请求方法")
    private String requestMethod;

    /**
     * 请求地址
     */
    @ApiModelProperty(value = "请求地址")
    private String requestUri;

    /**
     * 请求类型 {GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}
     */
    @ApiModelProperty(value = "请求类型 {GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}")
    private String httpMethod;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String params;

    /**
     * 返回值
     */
    @ApiModelProperty(value = "返回值")
    private String result;

    /**
     * 异常详情信息
     */
    @ApiModelProperty(value = "异常详情信息")
    private String exDesc;

    /**
     * 异常描述
     */
    @ApiModelProperty(value = "异常描述")
    private String exDetail;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime finishTime;

    /**
     * 执行时间
     */
    @ApiModelProperty(value = "执行时间")
    private String time;

    /**
     * 浏览器
     */
    @ApiModelProperty(value = "浏览器")
    private String userAgent;

    /**
     * 0:否 1:是
     */
    @ApiModelProperty(value = "0:否 1:是")
    private Integer isDel;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    private static final long serialVersionUID = 1L;
}
