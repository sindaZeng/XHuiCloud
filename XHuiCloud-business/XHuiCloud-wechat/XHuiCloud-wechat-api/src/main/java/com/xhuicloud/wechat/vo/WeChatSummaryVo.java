package com.xhuicloud.wechat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/11/9
 */
@Data
public class WeChatSummaryVo {

    @ApiModelProperty(value = "公众号名称")
    private String name;

    @ApiModelProperty(value = "公众号AppId")
    private String appId;

    @ApiModelProperty(value = "新增的用户数量")
    private Integer newUser;

    @ApiModelProperty(value = "取消关注的用户数量")
    private Integer cancelUser;

    @ApiModelProperty(value = "合计用户数量")
    private Integer totalUser;

}
