package com.sungyeh.bean.linstener;

import com.sungyeh.security.RecaptchaAuthenticationDetails;
import com.sungyeh.service.AsyncService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationSuccessEventListener
        implements ApplicationListener<AuthenticationSuccessEvent> {

    @Resource
    private AsyncService asyncService;

    @Resource
    private HttpServletRequest httpServletRequest;

    public void onApplicationEvent(AuthenticationSuccessEvent e) {

        if (e.getAuthentication().getDetails() instanceof RecaptchaAuthenticationDetails auth) {
            String remoteAddress = httpServletRequest.getHeader("X-Forwarded-For");
            String lat = auth.getLat();
            String lng = auth.getLng();
            asyncService.postToLine(remoteAddress, lat, lng);
        }
    }
}
