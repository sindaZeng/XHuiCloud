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
