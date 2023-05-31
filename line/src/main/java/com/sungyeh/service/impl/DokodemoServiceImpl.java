package com.sungyeh.service.impl;

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
import com.sungyeh.config.LineCustomConfig;
import com.sungyeh.service.DokodemoService;
import com.sungyeh.service.FirebaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * DokodemoServiceImpl
 *
 * @author sungyeh
 */
@Service("com.sungyeh.service.impl.DokodemoServiceImpl")
@Slf4j
public class DokodemoServiceImpl implements DokodemoService {

    /**
     * restTemplate
     */
    private final RestTemplate restTemplate = new RestTemplate();
    /**
     * firebase服務
     */
    @Resource
    private FirebaseService firebaseService;

    /**
     * line設定
     */
    @Resource
    private LineCustomConfig lineCustomConfig;

    @Override
    public void broadcast() {
        List<FreshSaleData> data = getFreshSale();
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
                List<String> users = firebaseService.getLineUsers();
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

    /**
     * 取得優惠
     *
     * @return 優惠資訊
     */
    private List<FreshSaleData> getFreshSale() {
        ResponseEntity<Event> event = restTemplate.getForEntity("https://dem-api.dokodemo.world/events?language_id=3", Event.class);
        log.info("event: {}", event.getBody());
        String currentFresh = String.format("https://dem-api.dokodemo.world/events/flash/items?event_id=%s&language_id=3&row_count=100&page_count=1", Objects.requireNonNull(event.getBody()).getEventData().get(0).getId());
        log.info("currentFresh: {}", currentFresh);
        ResponseEntity<FreshSale> freshSale = restTemplate.getForEntity(currentFresh, FreshSale.class);
        List<String> keyword = firebaseService.getKeywords();
        for (FreshSaleData datum : Objects.requireNonNull(freshSale.getBody()).getData()) {
            datum.setSalesStartedAt(reformatTime(datum.getSalesStartedAt()));
            datum.setSalesEndedAt(reformatTime(datum.getSalesEndedAt()));
        }
        return freshSale.getBody().getData().stream()
                .filter(obj -> keyword.size() == 0 || keyword.stream().anyMatch(k -> obj.getName().contains(k)))
                .toList();
    }

    /**
     * 轉換時間
     *
     * @param dateTimeString 時間字串
     * @return 轉換後時間
     */
    private String reformatTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        ZonedDateTime jpTime = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Tokyo"));
        ZonedDateTime twTime = jpTime.withZoneSameInstant(ZoneId.of("Asia/Taipei"));
        return twTime.format(formatter);
    }


}
