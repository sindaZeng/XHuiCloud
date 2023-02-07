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

package com.xhuicloud.logs.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.authorization.resource.annotation.Anonymous;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.logs.entity.AuditLogin;
import com.xhuicloud.logs.service.AuditLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
* @program: logs
* @description:
* @author: Sinda
* @create: 2022-03-19 20:42:34
*/
@RestController
@AllArgsConstructor
@RequestMapping("/auditLogin" )
@Api(value = "auditLogin", tags = "登录记录管理")
public class AuditLoginController {

    private final AuditLoginService auditLoginService;

    /**
    * 分页查询
    *
    * @param page 分页对象
    * @param auditLogin
    * @return Response
    */
    @GetMapping("/page" )
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public Response<Page> page(Page page, AuditLogin auditLogin) {
        page.setOrders(Arrays.asList(OrderItem.desc("login_time")));
        return Response.success(auditLoginService.page(page, Wrappers.query(auditLogin)));
    }

    /**
    * 新增
    *
    * @param auditLogin
    * @return Response
    */
    @Anonymous
    @AuditRecord("新增" )
    @PostMapping
    @ApiOperation(value = "新增", notes = "新增")
    public Response<Boolean> save(@RequestBody AuditLogin auditLogin) {
        return Response.success(auditLoginService.save(auditLogin));
    }

}
