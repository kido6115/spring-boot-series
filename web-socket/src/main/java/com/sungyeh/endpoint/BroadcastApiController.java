package com.sungyeh.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sungyeh.bean.Message;
import com.sungyeh.bean.OutputMessage;
import com.sungyeh.service.MsgTemplate;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

/**
 * TextCcsApiController
 *
 * @author sungyeh
 */
@RestController
@RequestMapping(value = "/api/text-ccs", method = RequestMethod.POST)
public class BroadcastApiController {

    @Resource
    private MsgTemplate template;


    @RequestMapping("/broadcast")
    public OutputMessage broadcast(@RequestBody Message message) {
        OutputMessage outputMessage = new OutputMessage(Calendar.getInstance().getTimeInMillis(), message);
        template.broadcast(outputMessage);
        return outputMessage;
    }

    @RequestMapping("/send/{user}")
    public OutputMessage broadcast(@PathVariable("user") String user, @RequestBody Message message) throws JsonProcessingException {
        OutputMessage outputMessage = new OutputMessage(Calendar.getInstance().getTimeInMillis(), message);
        ObjectMapper objectMapper = new ObjectMapper();
        template.sendMsgToUser(user, outputMessage);
        return outputMessage;
    }
}
