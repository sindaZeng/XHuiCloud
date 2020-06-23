package com.zsinda.fdp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserSocial implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 开放平台表id
     */
    private String socialType;

    /**
     * 用户openid
     */
    private String userOpenid;

    private static final long serialVersionUID = 1L;
}
