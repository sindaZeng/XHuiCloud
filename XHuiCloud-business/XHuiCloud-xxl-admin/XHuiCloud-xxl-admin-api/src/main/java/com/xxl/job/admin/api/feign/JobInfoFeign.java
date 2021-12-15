package com.xxl.job.admin.api.feign;

import com.xhuicloud.common.core.constant.ServiceNameConstants;
import com.xxl.job.admin.api.entity.XxlJobInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

@FeignClient(contextId = JobInfoFeign.JOBINFOFEIGN, value = ServiceNameConstants.XHUICLOUD_XXL_ADMIN_SERVICE)
public interface JobInfoFeign {

    String JOBINFOFEIGN = "jobInfoFeign";

    @PostMapping("/xxl-job-admin/jobinfo/add")
    @ResponseBody
    String add(@RequestHeader("Cookie") String cookie, @SpringQueryMap XxlJobInfo jobInfo);

    @PostMapping("/xxl-job-admin/jobinfo/addAndStart")
    @ResponseBody
    String addAndStart(@RequestHeader("Cookie") String cookie, @SpringQueryMap XxlJobInfo jobInfo);

}
