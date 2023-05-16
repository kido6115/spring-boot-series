package com.sungyeh.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * CSRF Request Matcher
 *
 * @author sungyeh
 */
public class CsrfRequestMatcher implements RequestMatcher {

    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    private List<String> excludeUrls = new ArrayList<>();

    @Override
    public boolean matches(HttpServletRequest request) {
        if (excludeUrls.contains(UrlUtils.buildRequestUrl(request))) {
            return false;
        }
        return !allowedMethods.matcher(request.getMethod()).matches();
    }

    public List<String> addExcludeUrl(String... urls) {
        excludeUrls.addAll(Arrays.asList(urls));
        return excludeUrls;
    }

    public List<String> removeExcludeUrl(String... urls) {
        excludeUrls.removeAll(Arrays.asList(urls));
        return excludeUrls;
    }

}
