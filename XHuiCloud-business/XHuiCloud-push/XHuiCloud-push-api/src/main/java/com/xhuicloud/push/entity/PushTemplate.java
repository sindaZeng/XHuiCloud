package com.xhuicloud.push.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="push_template")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "push_template")
public class PushTemplate extends Model<PushTemplate> {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="id")
    private Integer id;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户id")
    private Integer tenantId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;

    /**
     * 0: 否 1：是
     */
    @TableField(value = "is_del")
    @ApiModelProperty(value="0: 否 1：是")
    private Boolean isDel;

    /**
     * 创建者id
     */
    @TableField(value = "create_id")
    @ApiModelProperty(value = "创建者id", hidden = true)
    private Integer createId;

    /**
     * 更新者id
     */
    @TableField(value = "update_id")
    @ApiModelProperty(value = "更新者id", hidden = true)
    private Integer updateId;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 渠道
     */
    @TableField(value = "channel")
    @ApiModelProperty(value="渠道")
    private String channel;

    /**
     * 内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value="内容")
    private String content;

    /**
     * 0:正常 1:停用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="0:正常 1:停用")
    private Boolean status;

    /**
     * 参数对应的key与value
     */
    @TableField(value = "kv")
    @ApiModelProperty(value="参数对应的key与value")
    private String kv;

    /**
     * 第三方模板ID
     */
    @TableField(value = "template_id")
    @ApiModelProperty(value="第三方模板ID")
    private String templateId;

    /**
     * 类别标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value="类别标题")
    private String title;

}