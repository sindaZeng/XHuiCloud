import { TableColumn } from '@/components/XhTable/crud'

export const tableColumn: TableColumn[] = [
<#if columns??>
    <#list columns as column>
  {
    label: '${column.columnComment}',
    prop: '${column.smallColumnName}',
        <#if column.columnKey = 'PRI'>
    createDisplay: true,
    editDisabled: true,
        </#if>
    operationForm: {},
    searchForm: {}
  }<#if column_has_next>,</#if>
    </#list>
</#if>
]
