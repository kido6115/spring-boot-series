package com.sungyeh.service.impl;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingClientBuilder;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ImageCarouselColumn;
import com.linecorp.bot.model.message.template.ImageCarouselTemplate;
import com.sungyeh.config.LineCustomConfig;
import com.sungyeh.service.CwbImageAlt;
import com.sungyeh.service.CwbService;
import com.sungyeh.service.FirebaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * CwbServiceImpl
 *
 * @author sungyeh
 */
@Service("com.sungyeh.service.impl.CwbServiceImpl")
@Slf4j
public class CwbServiceImpl implements CwbService {

    private final static String URL = "https://www.cwb.gov.tw";

    @Resource
    private LineCustomConfig lineCustomConfig;

    @Resource
    private FirebaseService firebaseService;

    public List<String> getImage() {
        Document doc;
        try {
            doc = Jsoup.connect(URL + "/V8/C/").get();
        } catch (IOException e) {
            log.error("error: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return Arrays.asList(
                getImageByAlt(doc, CwbImageAlt.RADAR),
                getImageByAlt(doc, CwbImageAlt.RAINFALL),
                getImageByAlt(doc, CwbImageAlt.UV),
                getImageByAlt(doc, CwbImageAlt.TEMPERATURE)
        );
    }

    @Override
    public void weatherForecast() {
        List<String> images = getImage();
        LineMessagingClientBuilder builder = LineMessagingClient.builder(lineCustomConfig.getChannelToken());
        List<ImageCarouselColumn> columns = new ArrayList<>();

        for (String image : images) {
            ImageCarouselColumn imageCarouselColumn;
            try {
                imageCarouselColumn = new ImageCarouselColumn(new URI(image),
                        new URIAction("查看",
                                new URI("https://dokodemo.world/zh-Hant/item/"), null));
            } catch (URISyntaxException e) {
                log.error("URISyntaxException: {}", e.getMessage());
                throw new RuntimeException(e);
            }
            columns.add(imageCarouselColumn);
        }
        ImageCarouselTemplate template = new ImageCarouselTemplate(columns);
        TemplateMessage templateMessage = new TemplateMessage("近期氣象資訊", template);
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
    }

    private String getImageByAlt(Document document, CwbImageAlt alt) {
        String target = String.format("img[alt=\"%s\"]", alt.getAlt());
        log.info("target: {}", target);
        return URL + document.select(target).get(0).attr("src");
    }

}
