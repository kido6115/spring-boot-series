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
    /**
     * BROADCAST_DESTINATION
     */
    public static final String BROADCAST_DESTINATION = "/text-ccs/topic/messages";

    /**
     * USER_TOPIC
     */
    private static final String USER_TOPIC = "/subscribe";

    /**
     * WebSocketSessions
     */
    @Resource
    private WebSocketSessions sessions;

    /**
     * SimpMessagingTemplate
     */
    @Resource
    private SimpMessagingTemplate template;

    /**
     * 傳送訊息到不同路徑
     *
     * @param destination 路徑
     * @param msg         訊息
     */
    public void sendMsgTo(String destination, Object msg) {
        template.convertAndSend(destination, msg);
    }

    /**
     * 傳送訊息到指定session
     *
     * @param sessionId session id
     * @param msg       訊息
     */
    public void sendMsgToSession(String sessionId, Object msg) {
        template.convertAndSendToUser(sessionId, USER_TOPIC, msg);
    }

    /**
     * 傳送訊息到指定user
     *
     * @param user user
     * @param msg  訊息
     */
    public void sendMsgToUser(String user, Object msg) {
        sessions.getSessionIds(user).forEach(sessionId -> {
            template.convertAndSendToUser(sessionId, USER_TOPIC, msg);
        });
    }

    /**
     * 廣播
     *
     * @param msg 訊息
     */
    public void broadcast(Object msg) {
        sendMsgTo(BROADCAST_DESTINATION, msg);
    }
}
