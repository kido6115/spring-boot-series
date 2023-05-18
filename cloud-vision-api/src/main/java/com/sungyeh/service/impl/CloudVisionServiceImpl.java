package com.sungyeh.service.impl;

import com.sungyeh.config.VisionConfig;
import com.sungyeh.service.CloudVisionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * CloudVisionServiceImpl
 *
 * @author sungyeh
 */
@Service
@Slf4j
public class CloudVisionServiceImpl implements CloudVisionService {

    @Value("classpath:sample.json")
    org.springframework.core.io.Resource resourceFile;
    @Resource
    private VisionConfig config;

    public String send(String para) {
        InputStream stream = null;
        InputStreamReader isReader = null;
        BufferedReader reader = null;
        StringBuilder text = new StringBuilder();
        try {
            stream = resourceFile.getInputStream();
            isReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            reader = new BufferedReader(isReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("base64", para);
                text.append(line);
            }
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.info(e.getMessage(), e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.info(e.getMessage(), e);
                }
            }
            if (isReader != null) {
                try {
                    isReader.close();
                } catch (IOException e) {
                    log.info(e.getMessage(), e);
                }
            }
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(text.toString(), httpHeaders);
        String test = restTemplate.postForObject("https://vision.googleapis.com/v1/images:annotate?key=" + config.getKey(), request, String.class);
        return test;
    }
}
