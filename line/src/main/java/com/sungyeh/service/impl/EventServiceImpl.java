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
    private Map<Class<? extends MessageContent>, ContentService> map;

    private TextContentService textContentService;

    public EventServiceImpl(TextContentService textContentService) {
        this.map = new HashMap<>();
        this.map.put(AudioMessageContent.class, null);
        this.map.put(FileMessageContent.class, null);
        this.map.put(LocationMessageContent.class, null);
        this.map.put(StickerMessageContent.class, null);
        this.map.put(TextMessageContent.class, textContentService);
        this.map.put(UnknownMessageContent.class, null);
        this.map.put(VideoMessageContent.class, null);
    }

    @Override
    public Message execute(MessageContent content) {
        return map.get(content.getClass()).dispatch(content);
    }
}
