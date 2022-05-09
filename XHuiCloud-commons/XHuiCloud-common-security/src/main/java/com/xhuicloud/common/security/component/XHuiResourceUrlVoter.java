package com.xhuicloud.common.security.component;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;


@AllArgsConstructor
public class XHuiResourceUrlVoter implements AccessDecisionVoter<FilterInvocation> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return ACCESS_DENIED;
        }
        return ACCESS_GRANTED;
    }
}
