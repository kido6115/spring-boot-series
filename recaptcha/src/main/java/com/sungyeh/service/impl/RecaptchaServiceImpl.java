package com.sungyeh.service.impl;

import com.sungyeh.bean.CaptchaResult;
import com.sungyeh.config.CaptchaConfig;
import com.sungyeh.service.RecaptchaService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {

    private static final String verify = "https://www.google.com/recaptcha/api/siteverify";

    @Resource
    private CaptchaConfig config;

    @Override
    public boolean verify(String token) {
        return verify(new RestTemplate(), token);
    }

    @Override
    public boolean verify(RestOperations restOperations, String token) {
        boolean result = false;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", config.getSecret());
        map.add("response", token);
        HttpEntity<MultiValueMap<String, String>> requestHeader = new HttpEntity<>(map, headers);
        ResponseEntity<CaptchaResult> response = restOperations.postForEntity(verify,
                requestHeader, CaptchaResult.class);
        CaptchaResult captchaResult = response.getBody();
        if (captchaResult != null) {
            result = captchaResult.isSuccess();
        }
        return result;
    }
}
