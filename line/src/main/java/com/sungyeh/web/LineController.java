package com.sungyeh.web;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.sungyeh.service.EventService;
import jakarta.annotation.Resource;

/**
 * LineController
 *
 * @author sungyeh
 */
@LineMessageHandler
public class LineController {

    @Resource
    private EventService eventService;

    @EventMapping
    public Message handleTextMessageEvent(MessageEvent event) {
        return eventService.execute(event.getMessage());
    }


    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println(event);
    }
}
