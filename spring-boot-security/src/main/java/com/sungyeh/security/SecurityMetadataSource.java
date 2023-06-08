package com.sungyeh.security;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Security Metadata Source
 *
 * @author MJQ
 */
@Component
@Slf4j
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final String QUERY_PARAM_PATTERN = "(\\?[\\w\\.=&\\-\\+@:#;_]+)?";
    private static Map<String, Collection<ConfigAttribute>> resourceToRoles;
    private static Map<String, RegexRequestMatcher> resourceToRequestMatchers;

    static {
        resourceToRoles = new ConcurrentHashMap<>();
        resourceToRequestMatchers = new ConcurrentHashMap<>();
    }

    @Resource(name = "requestMappingHandlerMapping")
    @Setter
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public SecurityMetadataSource() {
    }

    /**
     * Load Resource
     */
    @PostConstruct
    private void loadResource() {
        loadBaseResource();
        loadFunctionResource();
    }

    /**
     * Reload Resource
     */
    public void reloadResource() {
        resourceToRoles.clear();
        resourceToRequestMatchers.clear();
        loadResource();
    }

    /**
     * Load Base Resource
     */
    private void loadBaseResource() {
//        List<String> roleNos = roleRepository.findActiveRoleNos();
//
//        Map<RequestMappingInfo, HandlerMethod> methods =
//                requestMappingHandlerMapping.getHandlerMethods();
//        for (Entry<RequestMappingInfo, HandlerMethod> entry : methods.entrySet()) {
//            RequestMappingInfo requestMappingInfo = entry.getKey();
//            HandlerMethod handlerMethod = entry.getValue();
//            Class<?> clazz = handlerMethod.getBeanType();
//
//            if (clazz.isAnnotationPresent(RestController.class)
//                    || handlerMethod.getMethod().isAnnotationPresent(ResponseBody.class)) {
//                PatternsRequestCondition condition = requestMappingInfo.getPatternsCondition();
//
//                if (condition != null) {
//                    Set<String> patterns = condition.getPatterns();
//                    for (String pattern : patterns) {
//                        resourceToRoles.put(
//                                pattern,
//                                SecurityConfig.createList(roleNos.stream().toArray(String[]::new)));
//
//                        String fixedPattern = processRequestMapping(
//                                pattern,
//                                Pattern.compile("\\{.+\\}"),
//                                "[-\\w\\.]+") + QUERY_PARAM_PATTERN;
//                        resourceToRequestMatchers.put(
//                                pattern,
//                                new RegexRequestMatcher(fixedPattern, null));
//                    }
//                }
//
//            }
//        }

        log.info("Loading Base Resource");
    }

    private void addResource(String urlPattern, String... roles) {
        resourceToRoles.put(urlPattern, SecurityConfig.createList(roles));
        resourceToRequestMatchers
                .put(urlPattern, new RegexRequestMatcher(urlPattern, null));
    }

    /**
     * Load Function Resource
     */
    private void loadFunctionResource() {
//        // anonymous resource
//        config.getAnonymousUrls().forEach(
//                url -> addResource(url, "ROLE_ANONYMOUS")
//        );
//
//        // All Protected Resource
//        List<String> urlPatterns = programService.findActiveUrlPatterns();
//        urlPatterns.forEach(urlPattern -> addResource(urlPattern, "ROLE_ADMIN"));
//
//        // Role Protected Resource
//        List<SimpleRule> rules = accessRuleService.findAllSimpleRules();
//        rules.forEach(rule -> {
//            if (resourceToRoles.containsKey(rule.getUrlPattern())) {
//                resourceToRoles
//                        .get(rule.getUrlPattern())
//                        .add(new SecurityConfig(rule.getRole()));
//            }
//        });

        log.info("Loading Function Resource");
    }

    private String processRequestMapping(String url, Pattern pattern, String replacement) {
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            url = processRequestMapping(
                    url.replace(matcher.group(), replacement), pattern, replacement);
        }

        return url;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        if (object instanceof RequestAuthorizationContext) {
            RequestMatcher requestMatcher;
            HttpServletRequest request = ((RequestAuthorizationContext) object).getRequest();

            for (Entry<String, Collection<ConfigAttribute>> entry :
                    resourceToRoles.entrySet()) {
                requestMatcher = resourceToRequestMatchers.get(entry.getKey());
                if (requestMatcher.matches(request)) {
                    return entry.getValue();
                }
            }
        }
        return SecurityConfig.createList("ROLE_NONE");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}
