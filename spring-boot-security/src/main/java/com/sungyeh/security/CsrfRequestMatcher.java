package com.sungyeh.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

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
     * 排除的路徑
     */
    private List<String> excludePaths = new ArrayList<>();

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
        for (String excludePath : excludePaths) {
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            if (antPathMatcher.match(excludePath, UrlUtils.buildRequestUrl(request))) {
                return false;
            }
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
     * 新增排除路徑
     *
     * @param paths 路徑
     * @return 排除paths
     */
    public List<String> addExcludePath(String... paths) {
        excludePaths.addAll(Arrays.asList(paths));
        return excludePaths;
    }


}
