package com.xhuicloud.push.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 推送组
    */
@ApiModel(value="推送组")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushGroup extends Model<PushGroup> {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="id")
    private Integer id;

    /**
     * 租户id
     */
    @ApiModelProperty(value="租户id")
    private Integer tenantId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;

    /**
     * 0: 否 1：是
     */
    @ApiModelProperty(value="0: 否 1：是")
    private Boolean isDel;

    /**
     * 创建者id
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建者id")
    private Integer createId;

    /**
     * 更新者id
     */
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value="更新者id")
    private Integer updateId;

    /**
     * 模板编码
     */
    @TableField(value = "template_code")
    @ApiModelProperty(value="模板编码")
    private String templateCode;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="名称")
    private String name;

}