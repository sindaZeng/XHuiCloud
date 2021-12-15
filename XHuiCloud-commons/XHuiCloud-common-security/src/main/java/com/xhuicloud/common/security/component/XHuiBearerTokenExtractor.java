package com.xhuicloud.common.security.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@RequiredArgsConstructor
public class XHuiBearerTokenExtractor extends BearerTokenExtractor {

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final PermitNoAuthUrlProperties permitNoAuthUrlProperties;

    @Override
    public Authentication extract(HttpServletRequest request) {
        boolean result = permitNoAuthUrlProperties.getIgnoreUrls().stream().anyMatch(url -> {
            List<String> strings = StrUtil.split(url, "|");
            boolean match = pathMatcher.match(strings.get(0), request.getRequestURI());
            if (strings.size() == 2) {
                List<String> methods = StrUtil.split(strings.get(1), StrUtil.COMMA);
                return CollUtil.contains(methods, request.getMethod()) && match;
            }
            return match;
        });
        return result ? null : super.extract(request);
    }
}
