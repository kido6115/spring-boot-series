package com.sungyeh.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * CommonInterceptor
 *
 * @author sungyeh
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {
    /**
     * httpSession
     */
    @Resource
    private HttpSession httpSession;

    /**
     * {@inheritDoc}
     * <p>
     * preHandle
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        if (httpSession.getAttribute("SPRING_SECURITY_CONTEXT") != null && modelAndView != null) {
            modelAndView.addObject("SPRING_SECURITY_CONTEXT", httpSession.getAttribute("SPRING_SECURITY_CONTEXT"));
        }
    }
}
