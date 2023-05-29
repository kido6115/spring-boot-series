package com.sungyeh.service.impl;

import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.sungyeh.service.TextContentResponseService;
import com.sungyeh.service.TextContentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TextContentServiceImpl
 *
 * @author sungyeh
 */
@Service("com.sungyeh.service.impl.TextContentServiceImpl")
public class TextContentServiceImpl implements TextContentService {

    private final List<TextContentResponseService> responseServiceList;
    private Map<String, TextContentResponseService> responseServiceMap;

    public TextContentServiceImpl(List<TextContentResponseService> responseServiceList) {
        this.responseServiceList = responseServiceList;
        this.responseServiceMap = new HashMap<>();
        for (TextContentResponseService textContentResponseService : this.responseServiceList) {
            responseServiceMap.put(textContentResponseService.getIntent(), textContentResponseService);
        }
    }

    @Override
    public Message dispatch(MessageContent messageContent) {
        if (messageContent instanceof TextMessageContent) {
            String text = ((TextMessageContent) messageContent).getText();
            if (text.startsWith("！")) {
                text = "!" + text.substring(1);
            }
            return responseServiceMap.get(text) == null ? defaultMessage(messageContent) : responseServiceMap.get(text).bulidMessage();
        } else {
            throw new RuntimeException("MessageContent is not TextMessageContent");
        }
    }

}
