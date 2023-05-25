package com.sungyeh.web;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

/**
 * LineController
 *
 * @author sungyeh
 */
@LineMessageHandler
public class LineController {


    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent event) {
        System.out.println(event);
//        LineMessagingClientBuilder builder = LineMessagingClient.builder("");
//        builder.build().multicast(
//                event.getSource().getSenderId(),
//                new TextMessage("Hello, world")
        return new TextMessage(event.getMessage().toString());
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println(event);
    }
}
