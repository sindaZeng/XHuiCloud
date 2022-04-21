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
<#if datePath??>
import { parseTime } from '@/utils/date'
</#if>
export const tableAttributes = {
  enableSearch: true, // 开启搜索栏 字段得添加可搜索属性!
  enableOperations: true, // 开启操作栏
  operationWidth: '250',
  columns: [
<#if columns??>
  <#list columns as column>
    {
      label: '${column.columnComment}',
      prop: '${column.smallColumnName}'<#if column.columnKey = 'PRI' || ["datetime", "date", "timestamp"]?seq_contains(column.dataType)>,</#if>
    <#if column.columnKey = 'PRI'>
      editDisabled: true,
      createDisabled: true
    </#if>
    <#if ["datetime", "date", "timestamp"]?seq_contains(column.dataType)>
      type: 'datetime',
      formatter: parseTime,
      editDisplay: true,
      createDisplay: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss'
    </#if>
    }<#if column_has_next>,</#if>
  </#list>
</#if>
  ]
}
