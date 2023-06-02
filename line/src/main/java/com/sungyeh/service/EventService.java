package com.sungyeh.service;


import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.message.Message;

/**
 * 事件處理器
 *
 * @author sungyeh
 */
public interface EventService {

    /**
     * @param content 傳入content
     * @return Message
     */
    Message execute(MessageContent content);
}
