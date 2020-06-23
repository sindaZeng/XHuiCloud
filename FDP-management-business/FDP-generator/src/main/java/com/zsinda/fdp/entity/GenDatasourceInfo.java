package com.zsinda.fdp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zsinda.fdp.data.Scalpel;
import com.zsinda.fdp.data.ScalpelTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "数据源")
public class GenDatasourceInfo implements Serializable {
    /**
     * 数据源表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "数据源表id")
    private Integer id;

    /**
     * 数据库类型:mysql,oracle,sqlsever
     */
    @ApiModelProperty(value = "数据库类型:mysql,oracle,sqlsever")
    private String type;

    /**
     * 数据库名
     */
    @ApiModelProperty(value = "数据库名")
    private String name;

    /**
     * 数据库host
     */
    @ApiModelProperty(value = "数据库host")
    private String host;

    /**
     * 数据库端口
     */
    @TableField(value = "port")
    @Scalpel(type = ScalpelTypeEnum.MOSAIC, before = 0, after = 0)
    @ApiModelProperty(value = "数据库端口")
    private String port;

    /**
     * 用户名
     */
    @Scalpel(type = ScalpelTypeEnum.MOSAIC, before = 0, after = 0)
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @Scalpel(type = ScalpelTypeEnum.MOSAIC, before = 0, after = 0)
    private String password;

    private static final long serialVersionUID = 1L;
}
