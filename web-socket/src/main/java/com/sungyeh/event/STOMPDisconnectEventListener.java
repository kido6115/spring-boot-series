package com.sungyeh.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tist.chat.bean.Message;
import com.tist.chat.bean.OutputMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
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

    @Resource
    private WebSocketSessions sessions;

    @Resource
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        sessions.removeSessionId(sessionId);
        String serve = sessions.getServeId(sessionId);
        if (serve != null) {
            MessageListenerContainer container = rabbitListenerEndpointRegistry.unregisterListenerContainer(serve + "-ccs");
            if (container != null) {
                container.stop();
            }
            Queue serveQueue = new Queue(serve + "-ccs", true, false, true);
            rabbitAdmin.declareQueue(serveQueue);
        } else {
            OutputMessage leaveOutputMessage = new OutputMessage();
            Message leaveMessage = new Message();
            leaveMessage.setText("已離線");
            leaveOutputMessage.setMessage(leaveMessage);
            try {
                this.rabbitTemplate.convertAndSend(sessionId + "-ccs", new ObjectMapper().writeValueAsString(leaveOutputMessage));
            } catch (JsonProcessingException e) {
                log.info(e.getMessage());
            }
            MessageListenerContainer container = rabbitListenerEndpointRegistry.unregisterListenerContainer(sessionId);
            if (container != null) {
                container.stop();
            }
        }
        log.info("user logout, sessionId:{}", sessionId);
        log.info(sessions.toString());
    }
}
