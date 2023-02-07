package com.xhuicloud.common.log.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/2/1
 */
@Data
public class AuditModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请求id")
    private String reqId;

    @ApiModelProperty(value = "操作IP")
    private String requestIp;

    @ApiModelProperty(value = "业务系统")
    private String serviceSystem;

    @ApiModelProperty(value = "操作人(id)")
    private String operator;

    @ApiModelProperty(value = "操作描述")
    private String detail;

    @ApiModelProperty(value = "类路径")
    private String classPath;

    @ApiModelProperty(value = "请求方法")
    private String requestMethod;

    @ApiModelProperty(value = "请求地址")
    private String requestUri;

    @ApiModelProperty(value = "请求类型 {GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}")
    private String httpMethod;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "返回值")
    private String result;

    @ApiModelProperty(value = "0: 失败 1: 成功")
    private Integer status;

    @ApiModelProperty(value = "异常描述")
    private String errorMsg;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
