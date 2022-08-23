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

package com.xhuicloud.upms.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xhuicloud.upms.dto.UserInfo;
import com.xhuicloud.common.core.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.xhuicloud.common.core.constant.AuthorizationConstants.FROM;

/**
 * @program: XHuiCloud
 * @description: SysSocialServiceFeign
 * @author: Sinda
 * @create: 2020-06-17 16:31
 */
@FeignClient(contextId = SysSocialServiceFeign.SYSSOCIALSERVICEFEIGN, value = ServiceNameConstants.XHUICLOUD_UPMS_SERVICE, path = "/social")
public interface SysSocialServiceFeign {

    String SYSSOCIALSERVICEFEIGN = "sysSocialServiceFeign";

    @GetMapping("/{type}/{code}")
    Response<UserInfo> getSysUser(@PathVariable(value = "type") String type, @PathVariable(value = "code") String code, @RequestHeader(FROM) String from);

    @PutMapping("/wechat/token")
    Response updateWechatToken(@RequestHeader(FROM) String from);
}
