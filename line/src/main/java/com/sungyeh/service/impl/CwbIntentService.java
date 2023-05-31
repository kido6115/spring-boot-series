package com.sungyeh.service.impl;

import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ImageCarouselColumn;
import com.linecorp.bot.model.message.template.ImageCarouselTemplate;
import com.sungyeh.service.CwbService;
import com.sungyeh.service.LineIntent;
import com.sungyeh.service.TextContentResponseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * CwbIntentService
 *
 * @author sungyeh
 */
@Service("com.sungyeh.service.impl.CwbIntentService")
@Slf4j
public class CwbIntentService implements TextContentResponseService {

    /**
     * 氣象服務
     */
    @Resource
    private CwbService cwbService;


    @Override
    public String getIntent() {
        return LineIntent.WEATHER.getIntent();
    }

    @Override
    public Message bulidMessage() {
        List<String> images = cwbService.getImage();
        List<ImageCarouselColumn> columns = new ArrayList<>();

        for (String image : images) {
            ImageCarouselColumn imageCarouselColumn;
            try {
                imageCarouselColumn = new ImageCarouselColumn(new URI(image + "?" + System.currentTimeMillis()),
                        new URIAction("查看",
                                new URI(image + "?" + System.currentTimeMillis()), null));
            } catch (URISyntaxException e) {
                log.error("URISyntaxException: {}", e.getMessage());
                throw new RuntimeException(e);

            }
            columns.add(imageCarouselColumn);
        }
        ImageCarouselTemplate template = new ImageCarouselTemplate(columns);
        return new TemplateMessage("近期氣象資訊", template);
    }
}
