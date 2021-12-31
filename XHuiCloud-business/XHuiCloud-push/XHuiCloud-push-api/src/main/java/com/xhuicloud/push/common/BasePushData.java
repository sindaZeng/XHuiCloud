/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.push.common;

import com.xhuicloud.push.enums.PushChannelEnum;
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
    private List<PushChannelEnum> pushChannelEnums;

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
