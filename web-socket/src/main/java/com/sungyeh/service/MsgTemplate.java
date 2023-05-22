package com.sungyeh.service;

import com.sungyeh.event.WebSocketSessions;
import jakarta.annotation.Resource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * MsgTemplate
 *
 * @author sungyeh
 */
@Service
public class MsgTemplate {
    public static final String BROADCAST_DESTINATION = "/text-ccs/topic/messages";
    private static final String USER_TOPIC = "/subscribe";

    @Resource
    private WebSocketSessions sessions;

    @Resource
    private SimpMessagingTemplate template;

    public void sendMsgTo(String destination, Object msg) {
        template.convertAndSend(destination, msg);
    }

    public void sendMsgToSession(String sessionId, Object msg) {
        template.convertAndSendToUser(sessionId, USER_TOPIC, msg);
    }

    public void sendMsgToUser(String user, Object msg) {
        sessions.getSessionIds(user).forEach(sessionId -> {
            template.convertAndSendToUser(sessionId, USER_TOPIC, msg);
        });
    }

    public void broadcast(Object msg) {
        sendMsgTo(BROADCAST_DESTINATION, msg);
    }
}
