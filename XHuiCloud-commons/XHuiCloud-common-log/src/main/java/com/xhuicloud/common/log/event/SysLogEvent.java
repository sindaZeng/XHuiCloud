package com.xhuicloud.common.log.event;


import com.xhuicloud.logs.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 系统日志事件
 * @author sinda
 * @date 2020年01月31日21:38:43
 */
@Data
@AllArgsConstructor
public class SysLogEvent  {

    private final SysLog sysLog;

}
