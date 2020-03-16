package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName(value = "sys_log")
public class SysLog implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作IP
     */
    @TableField(value = "request_ip")
    private String requestIp;

    /**
     * 日志类型 {0:操作类型;1:异常类型}
     */
    @TableField(value = "type")
    private String type;

    /**
     * 操作人
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 操作描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 类路径
     */
    @TableField(value = "class_path")
    private String classPath;

    /**
     * 请求方法
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 请求地址
     */
    @TableField(value = "request_uri")
    private String requestUri;

    /**
     * 请求类型 {GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}
     */
    @TableField(value = "http_method")
    private String httpMethod;

    /**
     * 请求参数
     */
    @TableField(value = "params")
    private String params;

    /**
     * 返回值
     */
    @TableField(value = "result")
    private String result;

    /**
     * 异常详情信息
     */
    @TableField(value = "ex_desc")
    private String exDesc;

    /**
     * 异常描述
     */
    @TableField(value = "ex_detail")
    private String exDetail;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 结束时间
     */
    @TableField(value = "finish_time")
    private LocalDateTime finishTime;

    /**
     * 执行时间
     */
    @TableField(value = "time")
    private String time;

    /**
     * 浏览器
     */
    @TableField(value = "user_agent")
    private String userAgent;

    @TableField(value = "del_flag")
    private Integer delFlag;


}
