package com.sungyeh.service.impl;

import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.sungyeh.service.LineIntent;
import com.sungyeh.service.TextContentResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * AskIntentService
 *
 * @author sungyeh
 */
@Service("com.sungyeh.service.impl.AskIntentService")
@Slf4j
public class AskIntentService implements TextContentResponseService {


    @Override
    public String getIntent() {
        return LineIntent.ASK.getIntent();
    }

    @Override
    public Message bulidMessage() {
        List<Action> actions = new ArrayList<>();
        for (LineIntent value : LineIntent.values()) {
            actions.add(new MessageAction(value.getIntent(), value.getIntent()));
        }
        ButtonsTemplate template = new ButtonsTemplate(null, "支援指令", "以下為支援的指令", actions);
        return new TemplateMessage("你會甚麼", template);
    }
}
