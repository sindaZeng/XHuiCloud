package com.xhuicloud.wechat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Desc
 * @Author Sinda
 * @Date 2023/1/11
 */
@Data
public class WeChatAccountVo {

    @ApiModelProperty(value = "公众号名称")
    private String name;

    @ApiModelProperty(value = "公众号头像")
    private String url;

}
