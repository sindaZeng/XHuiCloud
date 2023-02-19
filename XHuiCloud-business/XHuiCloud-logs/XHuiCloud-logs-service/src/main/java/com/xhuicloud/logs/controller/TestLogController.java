package com.xhuicloud.logs.controller;

import com.xhuicloud.common.authorization.resource.annotation.Anonymous;
import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.common.log.annotation.AuditRecord;
import com.xhuicloud.common.log.utils.LogRecordContext;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc 审计日志测试类
 * @Author Sinda
 * @Date 2023/2/1
 */
@RestController
@RequestMapping("/log")
@AllArgsConstructor
@Api(value = "log", tags = "审计日志测试")
public class TestLogController {

    /**
     * 匿名操作
     *
     * @return
     */
    @Anonymous(false)
    @GetMapping("/test1")
    @AuditRecord("'收件人从' + oldUser(#new1) + ',修改到 '+ newUser(#new2)")
    public Response test1() {
        LogRecordContext.putVariable("new1", "小明");
        LogRecordContext.putVariable("new2", "晓东");
        return Response.success();
    }

    public String oldUser(String str) {
        return str;
    }

    public String newUser(String str) {
        return str;
    }

    /**
     * 非匿名操作
     *
     * @return
     */
    @GetMapping("/test2")
    @AuditRecord("'收件人从' + oldUser(#new1) + ',修改到 '+ newUser(#new2)")
    public Response test2() {
        LogRecordContext.putVariable("new1", "小明");
        LogRecordContext.putVariable("new2", "晓东");
        return Response.success();
    }

    /**
     * 根据条件保存审计日志
     *
     * @return
     */
    @GetMapping("/test3/{id}")
    @AuditRecord(value = "审计保存条件", condition = "#id == 1")
    public Response test3(@PathVariable Long id) {
        return Response.success();
    }

}
