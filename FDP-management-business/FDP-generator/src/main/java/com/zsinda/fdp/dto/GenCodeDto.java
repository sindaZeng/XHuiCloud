package com.zsinda.fdp.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: FDPlatform
 * @description: GenCodeDto
 * @author: Sinda
 * @create: 2020-06-22 15:56
 */
@Data
public class GenCodeDto {

    /**
     * 表名
     */
    @NotNull
    private List<String> tableName;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 作者名
     */
    private String author;

}
