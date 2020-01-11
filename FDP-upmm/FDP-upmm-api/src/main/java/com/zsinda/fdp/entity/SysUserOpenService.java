package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_user_open_service")
public class SysUserOpenService implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private Integer userId;

    /**
     * 开放平台表id
     */
    @TableId(value = "open_service_id", type = IdType.INPUT)
    private Integer openServiceId;

    /**
     * 用户openid
     */
    @TableField(value = "user_openid")
    private String userOpenid;

    private static final long serialVersionUID = 1L;
}