package com.xhuicloud.push.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="推送单人单一内容")
public class PushSingle extends BasePushData{

    @ApiModelProperty(value = "用户id")
    @NotBlank
    private Integer userId;

}
