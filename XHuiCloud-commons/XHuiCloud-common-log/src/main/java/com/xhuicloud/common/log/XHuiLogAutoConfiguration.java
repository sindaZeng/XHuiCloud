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

package com.xhuicloud.common.log;

import com.xhuicloud.common.log.aspect.AuditRecordAspect;
import com.xhuicloud.common.log.component.IOperatorGetService;
import com.xhuicloud.common.mq.annotation.EnableXHuiRabbitMq;
import com.xhuicloud.common.mq.service.CommonMqService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @program: XHuiCloud
 * @description: LogAutoConfiguration
 * @author: Sinda
 * @create: 2020-02-01 01:05
 */
@EnableAsync
@EnableXHuiRabbitMq
@AllArgsConstructor
@ConditionalOnWebApplication
public class XHuiLogAutoConfiguration {

    private final CommonMqService commonMqService;

    @Bean
    public AuditRecordAspect auditRecordAspect(
            @Autowired(required = false) IOperatorGetService operatorGetService) {
        return new AuditRecordAspect(commonMqService, operatorGetService);
    }
}
