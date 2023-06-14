package com.sungyeh.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
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

    @Value("${google.api.key}")
    private String apiKey;

    @Override
    @Async("executor")
    public void postToLine(String remoteAddress, String lat, String lng) {
        String threadName = Thread.currentThread().getName();
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        String city = "";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            GeocodingApiRequest request = new GeocodingApiRequest(context);
            request.latlng(latLng);
            request.language("zh-TW");
            request.resultType(AddressType.ADMINISTRATIVE_AREA_LEVEL_1);
            GeocodingResult[] results = request.await();
            city = gson.toJson(results[0].addressComponents[0].shortName);
            context.shutdown();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = lineServer + "/firebase/add-location";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("ip", remoteAddress);
        map.add("lat", lat);
        map.add("lng", lng);
        map.add("city", city);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> res = restTemplate.postForEntity(url, entity, String.class);
        log.info(threadName + " : {}", res.getBody());
    }
}
