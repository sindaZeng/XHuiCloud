package com.xhuicloud.job.xxlJob;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.xxl.job.core.biz.model.ReturnT.SUCCESS;

@Slf4j
@Component
public class DemoJobHandler {

    @XxlJob("demoJobHandler")
    public ReturnT<String> demoJobHandler() {
        String param = XxlJobHelper.getJobParam();
        log.info(param);
        System.out.println(param);
        return SUCCESS;
    }

}
