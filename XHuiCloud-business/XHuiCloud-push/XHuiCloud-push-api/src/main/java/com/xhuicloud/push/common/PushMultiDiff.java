package com.xhuicloud.push.common;

import com.xhuicloud.push.enums.PushChannelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="推送多人不同内容")
public class PushMultiDiff extends BasePush{

    @ApiModelProperty("每个用户发送的模板消息集合")
    private List<Push> templates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Push {

        @ApiModelProperty(value = "用户id")
        private Integer userId;

        @ApiModelProperty(value = "模板组编码")
        private String code;

        @ApiModelProperty("模板渠道，当不选择模板渠道时")
        private List<PushChannelEnum> pushChannelEnums;

        @ApiModelProperty("模板参数集合")
        private List<BasePushData.Parameter> params;
    }
}
