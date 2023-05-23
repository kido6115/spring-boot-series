package com.sungyeh.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sungyeh.bean.Message;
import com.sungyeh.bean.OutputMessage;
import com.sungyeh.service.MsgTemplate;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

/**
 * BroadcastApiController
 *
 * @author sungyeh
 */
@RestController
@RequestMapping(value = "/api/text-ccs", method = RequestMethod.POST)
public class BroadcastApiController {

    /**
     * MsgTemplate
     */
    @Resource
    private MsgTemplate template;


    /**
     * 廣播端點
     *
     * @param message 訊息主體
     * @return OutputMessage
     */
    @RequestMapping("/broadcast")
    public OutputMessage broadcast(@RequestBody Message message) {
        OutputMessage outputMessage = new OutputMessage(Calendar.getInstance().getTimeInMillis(), message);
        template.broadcast(outputMessage);
        return outputMessage;
    }

    /**
     * 堆送至指定使用者
     *
     * @param user    使用者
     * @param message 訊息主體
     * @return OutputMessage
     */
    @RequestMapping("/send/{user}")
    public OutputMessage broadcast(@PathVariable("user") String user, @RequestBody Message message) {
        OutputMessage outputMessage = new OutputMessage(Calendar.getInstance().getTimeInMillis(), message);
        ObjectMapper objectMapper = new ObjectMapper();
        template.sendMsgToUser(user, outputMessage);
        return outputMessage;
    }
}
