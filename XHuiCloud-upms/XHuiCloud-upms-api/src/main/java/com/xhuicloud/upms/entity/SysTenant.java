package com.xhuicloud.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xhuicloud.common.core.annotation.Scalpel;
import com.xhuicloud.common.core.data.ScalpelTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户表
 */
@Data
@ApiModel(value="租户表")
public class SysTenant implements Serializable {
    /**
     * 租户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="租户id")
    private Integer id;

    /**
     * 租户名称
     */
    @ApiModelProperty(value="租户名称")
    private String name;

    /**
     * 状态 0:禁用; 1:正常;  2:待审核;  3:拒绝
     */
    @ApiModelProperty(value="状态,0:禁用; 1:正常;  2:待审核;  3:拒绝")
    private Integer state;

    /**
     * 有效期
     */
    @ApiModelProperty(value="有效期")
    private LocalDateTime expirationTime;

    /**
     * logo地址
     */
    @ApiModelProperty(value="logo地址")
    @Scalpel(type = ScalpelTypeEnum.ADD_DOMAIN)
    private String logo;

    /**
     * 租户描述
     */
    @ApiModelProperty(value="租户描述")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private LocalDateTime updateTime;

    /**
     * 0:否 1:是
     */
    @ApiModelProperty(value="0:否 1:是")
    private Integer isDel;

    private static final long serialVersionUID = 1L;
}
