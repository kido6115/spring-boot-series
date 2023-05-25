package com.sungyeh.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingClientBuilder;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.sungyeh.bean.dokodemo.Event;
import com.sungyeh.bean.dokodemo.FreshSale;
import com.sungyeh.bean.dokodemo.FreshSaleData;
import com.sungyeh.bean.firebase.DokodemoPrefer;
import com.sungyeh.bean.firebase.IdTokenResponse;
import com.sungyeh.bean.firebase.LineID;
import com.sungyeh.config.FirebaseInfoConfig;
import com.sungyeh.config.LineCustomConfig;
import com.sungyeh.service.DokodemoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * DokodemoServiceImpl
 *
 * @author sungyeh
 */
@Service("com.sungyeh.service.impl.DokodemoServiceImpl")
@Slf4j
public class DokodemoServiceImpl implements DokodemoService {

    private final RestTemplate restTemplate = new RestTemplate();


    @Resource
    private FirebaseInfoConfig firebaseInfoConfig;

    @Resource
    private LineCustomConfig lineCustomConfig;

    @Override
    public void broadcast() {
        String idToken = getIdToken();
        List<FreshSaleData> data = getFreshSale(idToken);
        LineMessagingClientBuilder builder = LineMessagingClient.builder(lineCustomConfig.getChannelToken());
        List<CarouselColumn> carouselColumns = new ArrayList<>();
        int count = 0;
        for (FreshSaleData datum : data) {
            String image = String.format("https://image.dokodemo.world%s?d=300x0", datum.getMediaContents().get(0).getPath());
            CarouselColumn carouselColumn;
            try {
                carouselColumn = new CarouselColumn(new URI(image),
                        String.format("%s - %s%%",
                                datum.getName().length() > 30 ? datum.getName().substring(0, 30) : datum.getName(),
                                datum.getDiscountRate()),
                        String.format("開始於 : %s\n結束於 : %s", datum.getSalesStartedAt(), datum.getSalesEndedAt()),
                        Collections.singletonList(new URIAction("查看",
                                new URI("https://dokodemo.world/zh-Hant/item/" + datum.getId()), null)));
            } catch (URISyntaxException e) {
                log.error("URISyntaxException: {}", e.getMessage());
                throw new RuntimeException(e);
            }
            carouselColumns.add(carouselColumn);
            count++;
            if (carouselColumns.size() == 10 || count == data.size()) {
                CarouselTemplate carouselTemplate = CarouselTemplate.builder().imageAspectRatio("square").columns(carouselColumns).build();
                TemplateMessage templateMessage = new TemplateMessage("多和夢限時特惠", carouselTemplate);
                List<String> users = getLineUsers(idToken);
                users.forEach(u -> {
                            try {
                                builder.build().pushMessage(new PushMessage(u, templateMessage)).get();
                            } catch (InterruptedException | ExecutionException e) {
                                log.error("InterruptedException | ExecutionException: {}", e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                );
                carouselColumns.clear();
            }
        }
    }


    private List<FreshSaleData> getFreshSale(String idToken) {
        ResponseEntity<Event> event = restTemplate.getForEntity("https://dem-api.dokodemo.world/events?language_id=3", Event.class);
        log.info("event: {}", event.getBody());
        String currentFresh = String.format("https://dem-api.dokodemo.world/events/flash/items?event_id=%s&language_id=3&row_count=100&page_count=1", Objects.requireNonNull(event.getBody()).getEventData().get(0).getId());
        log.info("currentFresh: {}", currentFresh);
        ResponseEntity<FreshSale> freshSale = restTemplate.getForEntity(currentFresh, FreshSale.class);
        List<String> keyword = getKeyword(idToken);
        for (FreshSaleData datum : Objects.requireNonNull(freshSale.getBody()).getData()) {
            datum.setSalesStartedAt(reformatTime(datum.getSalesStartedAt()));
            datum.setSalesEndedAt(reformatTime(datum.getSalesEndedAt()));
        }
        return freshSale.getBody().getData().stream()
                .filter(obj -> keyword.size() == 0 || keyword.stream().anyMatch(k -> obj.getName().contains(k)))
                .toList();
    }

    private String reformatTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        ZonedDateTime jpTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Tokyo"));
        ZonedDateTime twTime = jpTime.withZoneSameInstant(ZoneId.of("Asia/Taipei"));
        return twTime.format(formatter);
    }

    private String getIdToken() {
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

    private List<String> getKeyword(String idToken) {
        String url = "https://firestore.googleapis.com/v1/projects/sungyeh-tech-note/databases/(default)/documents/dokodemo-prefer";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + Objects.requireNonNull(idToken));
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<DokodemoPrefer> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, request, DokodemoPrefer.class);
        List<String> keyword = new ArrayList<>();
        if (response.getBody() != null && response.getBody().getDocuments() != null) {
            keyword = response.getBody().getDocuments().stream()
                    .map(d -> d.getFields().getKey().getStringValue()).toList();
        }

        return keyword;
    }

    private List<String> getLineUsers(String idToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://firestore.googleapis.com/v1/projects/sungyeh-tech-note/databases/(default)/documents/lineID";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + Objects.requireNonNull(idToken));
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<LineID> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, request, LineID.class);
        List<String> users = new ArrayList<>();
        if (response.getBody() != null && response.getBody().getDocuments() != null) {
            users = response.getBody().getDocuments().stream()
                    .map(d -> d.getFields().getOpenid().getStringValue()).toList();
        }

        return users;
    }
}
