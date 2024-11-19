package com.hexagon.events_service.service.notification;

import com.hexagon.events_service.publisher.RabbitMQJsonProducer;
import com.hexagon.events_service.dto.NotificationDTO;
import com.netflix.discovery.EurekaClient;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;
    private final RabbitMQJsonProducer jsonProducer;
    private final EurekaClient eurekaClient;

    @Autowired
    public NotificationService(RestTemplate restTemplate, RabbitMQJsonProducer jsonProducer, EurekaClient eurekaClient) {
        this.restTemplate = restTemplate;
        this.jsonProducer = jsonProducer;
        this.eurekaClient = eurekaClient;
    }

    public void notify(NotificationDTO notificationDTO) {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("NOTIFICATIONS", false);
        String notificationServiceUrl = instance.getHomePageUrl() + "/api/notifications";

        restTemplate.postForObject(notificationServiceUrl, notificationDTO, NotificationDTO.class);
        jsonProducer.sendJsonNotification(notificationDTO);
    }
}
