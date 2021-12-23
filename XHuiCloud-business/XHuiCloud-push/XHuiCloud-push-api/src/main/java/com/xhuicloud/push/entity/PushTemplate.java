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

@ApiModel(value="推送模板")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @ApiModelProperty(value = "创建者id", hidden = true)
    private Integer createId;

    /**
     * 更新者id
     */
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新者id", hidden = true)
    private Integer updateId;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String content;

    /**
     * 0:正常 1:停用
     */
    @ApiModelProperty(value="0:正常 1:停用")
    private Boolean status;

    /**
     * 参数对应的key与value
     */
    @ApiModelProperty(value="参数对应的key与value")
    private String kv;

    /**
     * 组id
     */
    @ApiModelProperty(value="组id")
    private Integer groupId;

    /**
     * 渠道
     */
    @ApiModelProperty(value="渠道")
    private String channel;

    /**
     * 渠道模板id
     */
    @ApiModelProperty(value="渠道模板id")
    private String channelId;

}