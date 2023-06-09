package com.sungyeh.security;

import com.sungyeh.bean.RoleAcl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * CustomAuthorizationManager
 *
 * @author sungyeh
 */
@Component("com.tist.security.CustomAuthorizationManager")
@Slf4j
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final Map<String, List<String>> MATCHER_MAP;

    static {
        MATCHER_MAP = new HashMap<>();
    }

    @Value("${system.url}")
    private String systemUrl;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {

        HttpServletRequest request = object.getRequest();
        //AJAX跳過
        boolean granted = "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
        for (GrantedAuthority grantedAuthority : authentication.get().getAuthorities()) {
            if (MATCHER_MAP.get(grantedAuthority.getAuthority()) != null) {
                for (String pattern : MATCHER_MAP.get(grantedAuthority.getAuthority())) {
                    RegexRequestMatcher regexRequestMatcher = new RegexRequestMatcher(pattern, null);
                    if (regexRequestMatcher.matches(request)) {
                        granted = true;
                        break;
                    }
                }
            }
        }

        return new AuthorizationDecision(granted);
    }

    /**
     * 初始化權限
     */
    @EventListener(ApplicationReadyEvent.class)
    public void getRoleAcls() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RoleAcl> responseEntity = restTemplate.getForEntity(systemUrl + "/acl/user", RoleAcl.class);
        for (RoleAcl.Acl acl : responseEntity.getBody().getAcls()) {
            List<String> antPathMatchers = MATCHER_MAP.computeIfAbsent(acl.getRole(), k -> new ArrayList<>());
            antPathMatchers.add(acl.getUrl());
        }
    }
}
