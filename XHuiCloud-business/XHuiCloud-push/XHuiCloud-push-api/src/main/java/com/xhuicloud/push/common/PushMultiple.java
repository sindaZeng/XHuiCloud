package com.xhuicloud.push.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="推送多人单一内容")
public class PushMultiple extends BasePushData{

    @ApiModelProperty(value = "用户id")
    @NotNull
    private List<Integer> userIds;

}

