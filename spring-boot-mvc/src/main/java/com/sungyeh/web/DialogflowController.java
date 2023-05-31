package com.sungyeh.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sungyeh.bean.dialogflow.DialogResponse;
import com.sungyeh.bean.dialogflow.OutputContext;
import com.sungyeh.bean.dialogflow.WebhookRequest;
import com.sungyeh.domain.Booking;
import com.sungyeh.repository.BookingRepository;
import com.sungyeh.utils.DialogflowUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

/**
 * DialogflowCOntroller
 *
 * @author sungyeh
 */

@RestController("com.sungyeh.web.DialogflowController")
public class DialogflowController {

    @Resource
    private BookingRepository bookingRepository;

    @PostMapping("/dialogflow/hook")
    public DialogResponse hook(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        WebhookRequest webhookRequest = objectMapper.readValue(json, WebhookRequest.class);

        OutputContext outputContext = webhookRequest.getQueryResult().getOutputContexts()
                .stream().filter(context -> context.getName()
                        .endsWith("/contexts/booking"))
                .findFirst().orElse(null);
        String date = outputContext.getParameters().get("date");
        String number = outputContext.getParameters().get("number");
        String dateTime = outputContext.getParameters().get("date-time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

        LocalDateTime localDateTime1 = LocalDateTime.parse(date, formatter);
        LocalDateTime localDateTime2 = LocalDateTime.parse(dateTime, formatter);

        LocalDateTime mergedDateTime = LocalDateTime.of(localDateTime1.toLocalDate(), localDateTime2.toLocalTime());
        Booking booking = new Booking();
        booking.setPeople(Math.round(Float.parseFloat(number)));
        booking.setDateTime(mergedDateTime);
        booking.setCreateTime(LocalDateTime.now());

        bookingRepository.save(booking);
        return DialogflowUtil.createFulfillmentText(Collections.singletonList("訂單編號為 : " + booking.getId() + ", 請重新整理頁面可暫時看到訂單"));
    }
}
