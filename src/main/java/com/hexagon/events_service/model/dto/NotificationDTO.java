package com.hexagon.events_service.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationDTO implements Serializable {
    private String message;
    private Long id;
    private Long eventId;
    private LocalDateTime sentAt;
}
