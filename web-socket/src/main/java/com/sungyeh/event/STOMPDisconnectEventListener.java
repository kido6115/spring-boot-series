package com.sungyeh.event;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * STOMPDisconnectEventListener
 *
 * @author sungyeh
 */
@Component
@Slf4j
public class STOMPDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

    /**
     * WebSocketSessions
     */
    @Resource
    private WebSocketSessions sessions;


    /**
     * {@inheritDoc}
     * <p>
     * 中斷websocket時觸發
     */
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        sessions.removeSessionId(sessionId);
        log.info("user logout, sessionId:{}", sessionId);
        log.info(sessions.toString());
    }
}
