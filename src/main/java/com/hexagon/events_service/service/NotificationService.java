package com.hexagon.events_service.service.notification;

import com.hexagon.events_service.publisher.RabbitMQJsonProducer;
import com.hexagon.events_service.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    private static final String NOTIFICATION_SERVICE_URL = "https://events-service-qn4k.onrender.com";
    private final RestTemplate restTemplate;
    private RabbitMQJsonProducer jsonProducer;

    @Autowired
    public NotificationService(RestTemplate restTemplate,RabbitMQJsonProducer jsonProducer) {
        this.restTemplate = restTemplate;
        this.jsonProducer = jsonProducer;
    }

    
    public void notify(NotificationDTO notificationDTO) {
        restTemplate.postForObject(NOTIFICATION_SERVICE_URL, notificationDTO, NotificationDTO.class);
        jsonProducer.sendJsonNotification(notificationDTO);
    }
}
