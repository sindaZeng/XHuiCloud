package com.xhuicloud.upms.dto;

import com.xhuicloud.upms.vo.FilterVo;
import com.xhuicloud.upms.vo.PredicateVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Desc
 * @Author Sinda
 * @Date 2022/10/31
 */
@Data
public class RouteConfDto {

    @ApiModelProperty(value="主键")
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 路由名称
     */
    @ApiModelProperty(value="路由名称")
    private String routeName;

    /**
     * 路由id
     */
    @ApiModelProperty(value="路由id")
    private String routeId;

    /**
     * 路由地址
     */
    @ApiModelProperty(value="路由地址")
    private String uri;

    @ApiModelProperty(value="谓词")
    private List<PredicateVo> predicateVos;

    @ApiModelProperty(value="过滤器")
    private List<FilterVo> filterVos;

}
