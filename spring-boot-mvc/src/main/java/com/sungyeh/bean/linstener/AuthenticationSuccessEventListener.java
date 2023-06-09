package com.sungyeh.bean.linstener;

import com.sungyeh.security.RecaptchaAuthenticationDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class AuthenticationSuccessEventListener
        implements ApplicationListener<AuthenticationSuccessEvent> {

    @Value("${line.server}")
    private String lineServer;

    public void onApplicationEvent(AuthenticationSuccessEvent e) {

        if (e.getAuthentication().getDetails() instanceof RecaptchaAuthenticationDetails auth) {
            String remoteAddress = auth.getRemoteAddress();
            String lat = auth.getLat();
            String lng = auth.getLng();
            try {
                postToLine(remoteAddress, lat, lng);
            } catch (Exception exception) {
                log.info(exception.getMessage(), exception);
            }
        }
    }

    @Async
    public void postToLine(String remoteAddress, String lat, String lng) {
        RestTemplate restTemplate = new RestTemplate();
        String url = lineServer + "/firebase/add-location";
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("ip", remoteAddress);
        map.add("lat", lat);
        map.add("lng", lng);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        restTemplate.postForEntity(url, entity, String.class);
    }
}
