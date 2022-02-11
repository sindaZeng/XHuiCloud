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


package com.xhuicloud.common.security.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.xhuicloud.common.security.annotation.Anonymous;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @program: XHuiCloud
 * @description: 资源服务器对外直接暴露URL, 如果设置contex-path 要特殊处理
 * @author: Sinda
 * @create: 2019年12月28日20:46:56
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnExpression("!'${security.oauth2.client.ignore-urls}'.isEmpty()")
@ConfigurationProperties(prefix = "security.oauth2.client")
public class PermitAnonymousUrlProperties implements InitializingBean {

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    private static final PathMatcher PATHMATCHER = new AntPathMatcher();

    private final WebApplicationContext applicationContext;

    /**
     * 拿到所有需要忽略授权认证的的url在下面方法中放行
     * {@link XHuiResourceServerConfigurerAdapter#configure(HttpSecurity)}
     */
    @Getter
    @Setter
    private List<String> ignoreUrls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        for (RequestMappingInfo info : map.keySet()) {
            HandlerMethod handlerMethod = map.get(info);

            // 1. 首先获取类上边 @Anonymous 注解
            Anonymous controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Anonymous.class);

            // 2. 当类上不包含 @Anonymous 注解则获取该方法的注解
            if (controller == null) {
                Anonymous method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Anonymous.class);
                Optional.ofNullable(method).ifPresent(inner -> info.getPatternsCondition().getPatterns()
                        .forEach(url -> this.filterPath(url, info, map)));
                continue;
            }

            // 3. 当类上包含 @Anonymous 注解 判断handlerMethod 是否包含在 inner 类中
            Class<?> beanType = handlerMethod.getBeanType();
            Method[] methods = beanType.getDeclaredMethods();
            Method method = handlerMethod.getMethod();
            if (ArrayUtil.contains(methods, method)) {
                info.getPatternsCondition().getPatterns().forEach(url -> filterPath(url, info, map));
            }
        }
    }

    /**
     * 过滤 Anonymous 设置
     * <p>
     * 0. 暴露安全检查 1. 路径转换： 如果为restful(/xx/{xx}) --> /xx/* ant 表达式 2.
     * 构建表达式：允许暴露的接口|允许暴露的方法类型,允许暴露的方法类型 URL|GET,POST,DELETE,PUT
     * </p>
     *
     * @param url  mapping路径
     * @param info 请求犯法
     * @param map  路由映射信息
     */
    private void filterPath(String url, RequestMappingInfo info, Map<RequestMappingInfo, HandlerMethod> map) {
        // 安全检查
        security(url, info, map);
        List<String> methodList = info.getMethodsCondition().getMethods().stream().map(RequestMethod::name)
                .collect(Collectors.toList());
        String resultUrl = ReUtil.replaceAll(url, PATTERN, "*");
        if (CollUtil.isEmpty(methodList)) {
            ignoreUrls.add(resultUrl);
        } else {
            ignoreUrls.add(String.format("%s|%s", resultUrl, CollUtil.join(methodList, StrUtil.COMMA)));
        }
    }

    /**
     * @param url 接口路径
     * @param rq  当前请求的元信息
     * @param map springmvc 接口列表
     */
    private void security(String url, RequestMappingInfo rq, Map<RequestMappingInfo, HandlerMethod> map) {
        // 判断 URL 是否是 rest path 形式
        if (!StrUtil.containsAny(url, StrUtil.DELIM_START, StrUtil.DELIM_END)) {
            return;
        }

        for (RequestMappingInfo info : map.keySet()) {
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            // 如果请求方法不匹配跳过
            if (!CollUtil.containsAny(methods, rq.getMethodsCondition().getMethods())) {
                continue;
            }

            // 如果请求方法路径匹配
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            for (String pattern : patterns) {
                // 跳过自身
                if (StrUtil.equals(url, pattern)) {
                    continue;
                }

                if (PATHMATCHER.match(url, pattern)) {
                    HandlerMethod rqMethod = map.get(rq);
                    HandlerMethod infoMethod = map.get(info);
                    log.error("@anonymous 标记接口 ==> {}.{} 使用不当，会额外暴露接口 ==> {}.{}", rqMethod.getBeanType().getName(),
                            rqMethod.getMethod().getName(), infoMethod.getBeanType().getName(),
                            infoMethod.getMethod().getName());
                }
            }
        }
    }

    /**
     * 获取对外暴露的URL，注册到 spring security
     *
     * @param registry spring security context
     */
    public void registry(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        for (String url : getIgnoreUrls()) {
            List<String> strings = StrUtil.split(url, "|");

            // 仅配置对外暴露的URL ，则注册到 spring security的为全部方法
            if (strings.size() == 1) {
                registry.antMatchers(strings.get(0)).permitAll();
                continue;
            }

            // 当配置对外的URL|GET,POST 这种形式，则获取方法列表 并注册到 spring security
            if (strings.size() == 2) {
                for (String method : StrUtil.split(strings.get(1), StrUtil.COMMA)) {
                    registry.antMatchers(HttpMethod.valueOf(method), strings.get(0)).permitAll();
                }
                continue;
            }

            log.warn("{} 配置无效，无法配置对外暴露", url);
        }
    }
}
