package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
    * 代理商
    */
@ApiModel(value="com-zsinda-fdp-entity-Agent")
@Data
@TableName(value = "agent")
public class Agent implements Serializable {
    /**
     * id
     */
    @TableId(value = "ID", type = IdType.INPUT)
    @ApiModelProperty(value="id")
    private String id;

    /**
     * 名称
     */
    @TableField(value = "NAME")
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 联系地址
     */
    @TableField(value = "ADDRESS")
    @ApiModelProperty(value="联系地址")
    private String address;

    /**
     * 联系人
     */
    @TableField(value = "CONTACTS_USER")
    @ApiModelProperty(value="联系人")
    private String contactsUser;

    /**
     * 联系电话
     */
    @TableField(value = "CONTACTS_NO")
    @ApiModelProperty(value="联系电话")
    private String contactsNo;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "CREATE_BY")
    @ApiModelProperty(value="创建人")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "UPDATE_BY")
    @ApiModelProperty(value="更新人")
    private String updateBy;

    /**
     * 删除标记
     */
    @TableField(value = "IS_DEL")
    @ApiModelProperty(value="删除标记")
    private Integer isDel;

    private static final long serialVersionUID = 1L;
}
