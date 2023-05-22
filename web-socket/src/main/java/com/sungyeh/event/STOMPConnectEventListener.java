package com.sungyeh.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tist.chat.bean.Message;
import com.tist.chat.bean.OutputMessage;
import com.tist.chat.service.MsgTemplate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.List;

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

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MsgTemplate msgTemplate;

    @Resource
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String user = accessor.getNativeHeader("user").get(0);
        List<String> serve = accessor.getNativeHeader("serve");

        String sessionId = accessor.getSessionId();
        sessions.registerSessionId(user, sessionId);
        log.info("user login, user:{}, sessionId:{}", user, sessionId);
        log.info(sessions.toString());
        SimpleRabbitListenerContainerFactory factory = createFactory();
        if (serve != null) {
            String q2 = serve.get(0);
            sessions.registerServe(sessionId, q2);
            Queue serveQueue = new Queue(q2 + "-ccs", true, false, true);

            SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
            endpoint.setId(serveQueue.getName());
            endpoint.setQueues(serveQueue);
            endpoint.setMessageListener(message -> {
                ObjectMapper mapper = new ObjectMapper();
                OutputMessage outputMessage = null;
                try {
                    outputMessage = mapper.readValue(new String(message.getBody()), OutputMessage.class);
                } catch (JsonProcessingException e) {
                    log.info(e.getMessage());
                }
                try {
                    Thread.sleep(500); //websocket與MQ因非同步有時間差問題, 太早送給socket會造成丟失
                } catch (InterruptedException e) {
                    log.info(e.getMessage());
                }
                msgTemplate.sendMsgToSession(sessionId, outputMessage);
            });
            rabbitListenerEndpointRegistry.registerListenerContainer(endpoint, factory, true);
        } else {
            Queue q = new Queue(sessionId, true, false, true);
            Queue ccs = new Queue(sessionId + "-ccs", true, false, true);

            RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());
            rabbitAdmin.declareQueue(q);
            rabbitAdmin.declareQueue(ccs);
            OutputMessage preOutputMessage = new OutputMessage();
            Message preMessage = new Message();
            preMessage.setText("請先留言待人連線");
            preOutputMessage.setMessage(preMessage);
            try {
                this.rabbitTemplate.convertAndSend(sessionId, new ObjectMapper().writeValueAsString(preOutputMessage));
            } catch (JsonProcessingException e) {
                log.info(e.getMessage());
            }

            SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
            endpoint.setId(sessionId);
            endpoint.setQueues(q);
            endpoint.setMessageListener(message -> {

                ObjectMapper mapper = new ObjectMapper();
                OutputMessage outputMessage = null;
                try {
                    outputMessage = mapper.readValue(new String(message.getBody()), OutputMessage.class);
                } catch (JsonProcessingException e) {
                    log.info(e.getMessage());
                }
                msgTemplate.sendMsgToSession(sessionId, outputMessage);
            });
            rabbitListenerEndpointRegistry.registerListenerContainer(endpoint, factory, true);
        }
    }

    private SimpleRabbitListenerContainerFactory createFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitTemplate.getConnectionFactory());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(5);
        factory.setDefaultRequeueRejected(false);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
}
