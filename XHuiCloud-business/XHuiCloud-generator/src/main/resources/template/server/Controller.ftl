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

package ${package}.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
import ${package}.entity.${ClassName};
import ${package}.service.${ClassName}Service;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
* @program: ${moduleName}
* @description: ${tableComment}
* @author: ${author}
* @create: ${datetime}
*/
@RestController
@AllArgsConstructor
@RequestMapping("/${pathName}" )
@Api(value = "${pathName}", tags = "${tableComment}管理")
public class ${ClassName}Controller {

    private final ${ClassName}Service ${className}Service;

    /**
    * 分页查询
    *
    * @param page 分页对象
    * @param ${className} ${tableComment}
    * @return Response
    */
    @GetMapping("/page" )
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public Response page(Page page, ${ClassName} ${className}) {
        return Response.success(${className}Service.page(page, Wrappers.query(${className})));
    }


    /**
    * 通过id查询${tableComment}
    * @param id
    * @return Response
    */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询${tableComment}", notes = "通过id查询${tableComment}")
    public Response getById(@PathVariable Integer id) {
        return Response.success(${className}Service.getById(id));
    }

    /**
    * 新增${tableComment}
    *
    * @param ${className} ${tableComment}
    * @return Response
    */
    @AuditRecord("新增${tableComment}" )
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_${pathName}')" )
    @ApiOperation(value = "新增${tableComment}", notes = "新增${tableComment}")
    public Response save(@RequestBody ${ClassName} ${className}) {
        return Response.success(${className}Service.save(${className}));
    }

    /**
    * 修改${tableComment}
    *
    * @param ${className} ${tableComment}
    * @return Response
    */
    @AuditRecord("编辑${tableComment}" )
    @PutMapping
    @PreAuthorize("@authorize.hasPermission('sys_editor_${pathName}')" )
    @ApiOperation(value = "修改${tableComment}", notes = "修改${tableComment}")
    public Response update(@RequestBody ${ClassName} ${className}) {
        return Response.success(${className}Service.updateById(${className}));
    }

    /**
    * 通过id删除${tableComment}
    *
    * @param id
    * @return Response
    */
    @AuditRecord("通过id删除${tableComment}" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@authorize.hasPermission('sys_delete_${pathName}')" )
    @ApiOperation(value = "通过id删除${tableComment}", notes = "通过id删除${tableComment}")
    public Response delete(@PathVariable Integer id) {
        return Response.success(${className}Service.removeById(id));
    }

}
