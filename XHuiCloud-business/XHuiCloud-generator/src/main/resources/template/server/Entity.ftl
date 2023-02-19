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

package ${package}.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
<#if auto??>
import com.baomidou.mybatisplus.annotation.IdType;
</#if>
<#if hasBigDecimal??>
import java.math.BigDecimal;
</#if>
<#if datePath??>
import ${datePath};
</#if>


/**
* @program: ${moduleName}
* @description: ${tableComment}
* @author: ${author}
* @create: ${datetime}
*/
@Data
@TableName("${tableName}")
@ApiModel(value = "${tableComment}")
public class ${ClassName} implements Serializable{

    private static final long serialVersionUID = 1L;

<#if columns??>
    <#list columns as column>
    <#if column.columnKey = 'PRI'>

    @TableId<#if auto?? && column.extra = 'auto_increment'>(value = "${column.columnName}", type = IdType.ASSIGN_ID)</#if>
    </#if>
    @ApiModelProperty(value = "${column.columnComment}")
    private ${column.javaDataType} ${column.smallColumnName};

    </#list>
</#if>

}
