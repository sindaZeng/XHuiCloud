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

package com.xhuicloud.generator.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: XHuiCloud
 * @description: GenCodeDto
 * @author: Sinda
 * @create: 2020-06-22 15:56
 */
@Data
public class GenCodeDto {

    /**
     * 库id
     */
    private Long id;

    /**
     * 表名
     */
    private List<String> tableName;

    /**
     * 去表前缀 0后端 1前缀
     */
    private List<Integer> toReplace;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 上下文路径
     */
    private String contextPath;

    /**
     * 包路径
     */
    private String packagePath;

    /**
     * 作者名
     */
    private String author;

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 是否生成前端代码 0否 1是
     */
    private Integer genWeb = 0;

    /**
     * 0: ts 1: js
     */
    private Integer isTs = 0;

}
