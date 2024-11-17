package com.hexagon.events_service.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationDTO implements Serializable{
    private String type;
    private String message;
    private LocalDateTime timestamp;
}
