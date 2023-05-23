package com.sungyeh.event;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * STOMPConnectEventListener
 *
 * @author sungyeh
 */
@Component
@Slf4j
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    @Resource
    private WebSocketSessions sessions;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String user = accessor.getNativeHeader("user").get(0);

        String sessionId = accessor.getSessionId();
        sessions.registerSessionId(user, sessionId);
        log.info("user login, user:{}, sessionId:{}", user, sessionId);
        log.info(sessions.toString());

    }

}
