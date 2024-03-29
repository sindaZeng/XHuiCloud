<?xml version="1.0" encoding="UTF-8"?>

<!--
MIT License
Copyright <2021-2022>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
@Author: Sinda
@Email:  xhuicloud@163.com
-->

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${package}.mapper.${ClassName}Mapper">

    <resultMap id="BaseResultMap" type="${package}.entity.${ClassName}">
        <#list columns as column>
        <#if column.columnKey = 'PRI'>
        <id column="${column.columnName}" jdbcType="${column.dataType?upper_case}" property="${column.smallColumnName}"/>
        <#else>
        <result column="${column.columnName}" jdbcType="${column.dataType?upper_case}" property="${column.smallColumnName}"/>
        </#if>
        </#list>
    </resultMap>
</mapper>
