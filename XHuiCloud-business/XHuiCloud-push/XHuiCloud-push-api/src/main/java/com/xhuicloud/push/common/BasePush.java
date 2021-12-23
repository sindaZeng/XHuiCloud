package com.xhuicloud.push.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value="基础推送载体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePush implements Serializable {

    @ApiModelProperty("定时发送，如果小于当前时间则立即发送")
    private Date sendTime;

    @ApiModelProperty("租户Id")
    private Integer tenantId;

}
