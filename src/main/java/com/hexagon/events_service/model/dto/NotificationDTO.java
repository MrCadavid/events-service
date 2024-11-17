package com.hexagon.events_service.dto;

import lombok.Data;

@Data
public class NotificationDTO{
    private String type;
    private String message;
    private String timestamp;
}
