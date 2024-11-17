package com.hexagon.events_service.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexagon.events_service.dto.NotificationDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendJsonNotification(NotificationDTO notification) {
        try {
            String notificationJson = objectMapper.writeValueAsString(notification);
            rabbitTemplate.convertAndSend("notifications", notificationJson);
        } catch (JsonProcessingException e) {
            System.err.println("error to serialize dto" + e.getMessage());
        }
    }
}
