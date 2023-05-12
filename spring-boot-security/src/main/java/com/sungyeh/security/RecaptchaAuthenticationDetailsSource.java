package com.sungyeh.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class RecaptchaAuthenticationDetailsSource
        implements AuthenticationDetailsSource<HttpServletRequest, RecaptchaAuthenticationDetails> {

    @Override
    public RecaptchaAuthenticationDetails buildDetails(HttpServletRequest httpServletRequest) {
        return new RecaptchaAuthenticationDetails(httpServletRequest);
    }
}


