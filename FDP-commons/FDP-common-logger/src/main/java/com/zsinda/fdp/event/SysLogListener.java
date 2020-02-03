package com.zsinda.fdp.event;


import com.zsinda.fdp.entity.SysLog;
import com.zsinda.fdp.feign.SysLogServiceFeign;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import static com.zsinda.fdp.constant.AuthorizationConstants.IS_COMMING_INNER_YES;


/**
 * 异步监听日志事件
 *
 * @author sinda
 * @date 2020年01月31日21:39:45
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {

    private final SysLogServiceFeign sysLogServiceFeign;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent sysLogEvent) {
        SysLog sysLog = sysLogEvent.getSysLog();
        sysLogServiceFeign.save(sysLog, IS_COMMING_INNER_YES);
    }

}
