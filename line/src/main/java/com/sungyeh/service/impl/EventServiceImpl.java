package com.sungyeh.service.impl;

import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.Message;
import com.sungyeh.service.ContentService;
import com.sungyeh.service.EventService;
import com.sungyeh.service.TextContentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * EventServiceImpl
 *
 * @author sungyeh
 */

@Service("com.sungyeh.service.impl.EventServiceImpl")
public class EventServiceImpl implements EventService {

    /**
     * 服務對應表
     */
    private final Map<Class<? extends MessageContent>, ContentService> serviceMap;

    /**
     * @param textContentService 文字內容服務
     */
    public EventServiceImpl(TextContentService textContentService) {
        Map<Class<? extends MessageContent>, ContentService> map = new HashMap<>();
        map.put(AudioMessageContent.class, null);
        map.put(FileMessageContent.class, null);
        map.put(LocationMessageContent.class, null);
        map.put(StickerMessageContent.class, null);
        map.put(TextMessageContent.class, textContentService);
        map.put(UnknownMessageContent.class, null);
        map.put(VideoMessageContent.class, null);
        serviceMap = map;
    }

    @Override
    public Message execute(MessageContent content) {
        return this.serviceMap.get(content.getClass()).dispatch(content);
    }
}
