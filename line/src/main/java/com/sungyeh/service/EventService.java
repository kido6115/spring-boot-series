package com.sungyeh.service;


import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.message.Message;

public interface EventService {

    Message execute(MessageContent content);
}
