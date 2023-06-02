package com.sungyeh.config;

import com.sungyeh.interceptor.CommonInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * InterceptorConfig
 *
 * @author sungyeh
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     * 通用interceptor
     */
    @Resource
    private CommonInterceptor commonInterceptor;

    /**
     * {@inheritDoc}
     * <p>
     * 註冊interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor).addPathPatterns("/auth/**", "/oauth/**", "/google/**", "/line/**", "/", "/index");
    }
}
