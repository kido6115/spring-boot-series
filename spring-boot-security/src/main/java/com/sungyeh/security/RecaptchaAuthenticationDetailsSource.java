package com.sungyeh.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

/**
 * RecaptchaAuthenticationDetailsSource
 *
 * @author sungyeh
 */
@Component
public class RecaptchaAuthenticationDetailsSource
        implements AuthenticationDetailsSource<HttpServletRequest, RecaptchaAuthenticationDetails> {

    /**
     * {@inheritDoc}
     * <p>
     * 建構子
     */
    @Override
    public RecaptchaAuthenticationDetails buildDetails(HttpServletRequest httpServletRequest) {
        return new RecaptchaAuthenticationDetails(httpServletRequest);
    }
}


