package com.sungyeh.service.impl;

import com.sungyeh.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * AsyncServiceImpl
 *
 * @author sungyeh
 */
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    @Value("${line.server}")
    private String lineServer;

    @Override
    @Async("executor")
    public void postToLine(String remoteAddress, String lat, String lng) {
        String threadName = Thread.currentThread().getName();

        System.out.println(threadName + ":get employee data start");

        RestTemplate restTemplate = new RestTemplate();
        String url = lineServer + "/firebase/add-location";
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("ip", remoteAddress);
        map.add("lat", lat);
        map.add("lng", lng);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> res = restTemplate.postForEntity(url, entity, String.class);
        log.info(threadName + " : {}", res.getBody());
    }
}
