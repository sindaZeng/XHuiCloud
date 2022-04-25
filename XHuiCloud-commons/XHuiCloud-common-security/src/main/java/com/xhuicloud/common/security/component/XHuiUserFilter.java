package com.xhuicloud.common.security.component;

import com.xhuicloud.common.data.ttl.XHuiCommonThreadLocalHolder;
import com.xhuicloud.common.security.service.XHuiUser;
import com.xhuicloud.common.security.utils.SecurityHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XHuiUserFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityHolder.getAuthentication();
        if (authentication != null) {
            XHuiUser user = SecurityHolder.getUser(authentication);
            XHuiCommonThreadLocalHolder.setUser(user.getId());
        }
        filterChain.doFilter(servletRequest, servletResponse);
        XHuiCommonThreadLocalHolder.removeUser();
    }
}
