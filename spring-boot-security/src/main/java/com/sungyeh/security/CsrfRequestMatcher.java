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

    /**
     * 允許的方法
     */
    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    /**
     * 排除的網址
     */
    private List<String> excludeUrls = new ArrayList<>();

    /**
     * {@inheritDoc}
     * <p>
     * 過濾網址
     */
    @Override
    public boolean matches(HttpServletRequest request) {
        if (excludeUrls.contains(UrlUtils.buildRequestUrl(request))) {
            return false;
        }
        return !allowedMethods.matcher(request.getMethod()).matches();
    }

    /**
     * 新增排除網址
     *
     * @param urls 網址
     * @return 排除urls
     */
    public List<String> addExcludeUrl(String... urls) {
        excludeUrls.addAll(Arrays.asList(urls));
        return excludeUrls;
    }

    /**
     * 移除排除網址
     *
     * @param urls 網址
     * @return 排除urls
     */
    public List<String> removeExcludeUrl(String... urls) {
        excludeUrls.removeAll(Arrays.asList(urls));
        return excludeUrls;
    }

}
