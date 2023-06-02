package com.sungyeh.service;

import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

/**
 * Line 基礎文本
 */
public interface ContentService {
    /**
     * @param messageContent 傳入content
     * @return Message
     */
    Message dispatch(MessageContent messageContent);

    /**
     * @param messageContent 傳入content
     * @return Message
     */
    default Message defaultMessage(MessageContent messageContent) {
        return new TextMessage("我不清楚你再說甚麼");
    }


}
