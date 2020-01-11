package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_open_service")
public class SysOpenService implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 开放平台id
     */
    @TableField(value = "app_id")
    private String appId;

    /**
     * 开放平台密钥
     */
    @TableField(value = "`app_ secret`")
    private String appSecret;

    /**
     * 开放平台描述
     */
    @TableField(value = "app_desc")
    private String appDesc;

    /**
     * 重定向url
     */
    @TableField(value = "redirect_url")
    private String redirectUrl;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     *  0:已删除
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

    private static final long serialVersionUID = 1L;
}