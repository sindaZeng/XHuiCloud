package com.xhuicloud.push.common;

import com.xhuicloud.push.enums.PushTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="基础推送数据")
public class BasePushData extends BasePush {

    @ApiModelProperty(value = "模板编码")
    private String templateCode;

    @ApiModelProperty("模板渠道，当不选择模板渠道时，会对该模板组下的所有渠道进行消息推送")
    private List<PushTypeEnum> pushTypeEnums;

    @ApiModelProperty("模板参数集合")
    private List<Parameter> params;

    public List<Parameter> add(Parameter parameter) {
        if (this.params == null) {
            this.params = new ArrayList<>();
        }
        this.params.add(parameter);
        return this.params;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Parameter {

        @ApiModelProperty("key 对应在创建模板时kv字段的key")
        private String key;

        @ApiModelProperty("值")
        private String value;

    }
}
