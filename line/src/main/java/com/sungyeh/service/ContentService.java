package com.sungyeh.service;

import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

public interface ContentService {
    Message dispatch(MessageContent messageContent);

    default Message defaultMessage(MessageContent messageContent) {
        return new TextMessage("我不清楚你再說甚麼");
    }


}
