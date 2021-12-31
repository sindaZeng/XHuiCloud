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

package com.xxl.job.admin.api.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xxl.job.admin.api.entity.XxlJobInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

@FeignClient(contextId = JobInfoFeign.JOBINFOFEIGN, value = ServiceNameConstants.XHUICLOUD_XXL_ADMIN_SERVICE, path = "/xxl-job-admin/jobinfo")
public interface JobInfoFeign {

    String JOBINFOFEIGN = "jobInfoFeign";

    @ResponseBody
    @PostMapping("/add")
    String add(@RequestHeader("Cookie") String cookie, @SpringQueryMap XxlJobInfo jobInfo);

    @ResponseBody
    @PostMapping("/addAndStart")
    String addAndStart(@RequestHeader("Cookie") String cookie, @SpringQueryMap XxlJobInfo jobInfo);

}
