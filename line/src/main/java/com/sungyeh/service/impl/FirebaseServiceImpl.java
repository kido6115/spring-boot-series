package com.sungyeh.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.sungyeh.bean.firebase.*;
import com.sungyeh.config.FirebaseInfoConfig;
import com.sungyeh.service.FirebaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * FirebaseServiceImpl
 *
 * @author sungyeh
 */
@Service
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {

    /**
     * restTemplate
     */
    private final RestTemplate restTemplate = new RestTemplate();
    /**
     * firebase設定
     */
    @Resource
    private FirebaseInfoConfig firebaseInfoConfig;

    @Override
    public String getIdToken() {
        String uid = "some-uid";

        String customToken;
        try {
            customToken = FirebaseAuth.getInstance().createCustomToken(uid);
        } catch (FirebaseAuthException e) {
            log.error("error: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> map = new HashMap<>();
        map.put("token", customToken);
        map.put("returnSecureToken", Boolean.TRUE);
        ObjectMapper mapper = new ObjectMapper();

        String jsonData;
        try {
            jsonData = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error("error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithCustomToken?key=" + firebaseInfoConfig.getKey();
        ResponseEntity<IdTokenResponse> response = restTemplate.postForEntity(url, request, IdTokenResponse.class);
        return Objects.requireNonNull(response.getBody()).getIdToken();
    }

    @Override
    public List<String> getKeywords() {
        String url = "https://firestore.googleapis.com/v1/projects/sungyeh-tech-note/databases/(default)/documents/dokodemo-prefer";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + Objects.requireNonNull(getIdToken()));
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<DokodemoPrefer> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, request, DokodemoPrefer.class);
        List<String> keyword = new ArrayList<>();
        if (response.getBody() != null && response.getBody().getDocuments() != null) {
            keyword = response.getBody().getDocuments().stream()
                    .map(d -> d.getFields().getKey().getStringValue()).toList();
        }

        return keyword;
    }

    @Override
    public List<String> getLineUsers() {
        String url = "https://firestore.googleapis.com/v1/projects/sungyeh-tech-note/databases/(default)/documents/lineID";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + Objects.requireNonNull(getIdToken()));
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<LineID> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, request, LineID.class);
        List<String> users = new ArrayList<>();
        if (response.getBody() != null && response.getBody().getDocuments() != null) {
            users = response.getBody().getDocuments().stream()
                    .map(d -> d.getFields().getOpenid().getStringValue()).toList();
        }

        return users;
    }

    @Override
    public Map<String, Integer> groupLocation() {
        String url = "https://firestore.googleapis.com/v1/projects/sungyeh-tech-note/databases/(default)/documents/location";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + Objects.requireNonNull(getIdToken()));
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Location> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, request, Location.class);

        return response.getBody().getDocuments().stream()
                .filter(d -> StringUtils.hasText(d.getFields().getCity().getStringValue())).collect(
                        Collectors.groupingBy(d -> d.getFields().getCity().getStringValue(), Collectors.summingInt(d -> 1))
                );
    }

    @Override
    public String addLocation(LocationDocument document) throws JsonProcessingException {
        String url = "https://firestore.googleapis.com/v1/projects/sungyeh-tech-note/databases/(default)/documents/location";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Authorization", "Bearer " + Objects.requireNonNull(getIdToken()));
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(document).replaceAll("\\\\\\\"", "");
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return response.getBody();
    }
}
