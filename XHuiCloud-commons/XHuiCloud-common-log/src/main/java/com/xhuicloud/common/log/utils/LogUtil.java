///*
// * MIT License
// * Copyright <2021-2022>
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
// * of the Software, and to permit persons to whom the Software is furnished to do so,
// * subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in all
// * copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
// * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
// * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
// * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
// * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
// * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
// * @Author: Sinda
// * @Email:  xhuicloud@163.com
// */
//
//package com.xhuicloud.common.log.utils;
//
//import lombok.experimental.UtilityClass;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.io.PrintWriter;
//import java.io.StringWriter;
//
///**
// * @program: XHuiCloud
// * @description: LogUtil
// * @author: Sinda
// * @create: 2020-02-01 00:48
// */
//@UtilityClass
//public class LogUtil {
//
////    public SysLog getSysLog() {
////        HttpServletRequest request = ((ServletRequestAttributes) Objects
////                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
////        SysLog sysLog = new SysLog();
////        sysLog.setUserName(Objects.requireNonNull(getUsername()));
////        sysLog.setType(CommonConstants.LOG_OPT_TYPE);
////        sysLog.setRequestIp(ServletUtil.getClientIP(request));
////        sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
////        sysLog.setHttpMethod(request.getMethod());
////        sysLog.setCreateTime(LocalDateTime.now());
////        sysLog.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
////        return sysLog;
////    }
//
//    /**
//     * 获取用户名称(用户Id)
//     *
//     * @return username
//     */
//    private String getOperator() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null) {
//            return "";
//        }
//        return authentication.getName();
//    }
//
//    /**
//     * 获取堆栈信息
//     *
//     * @param throwable
//     * @return
//     */
//    public static String getStackTrace(Throwable throwable) {
//        StringWriter sw = new StringWriter();
//        try (PrintWriter pw = new PrintWriter(sw)) {
//            throwable.printStackTrace(pw);
//            return sw.toString();
//        }
//    }
//
//}
