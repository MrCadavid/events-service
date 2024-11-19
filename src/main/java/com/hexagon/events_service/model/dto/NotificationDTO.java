package com.hexagon.events_service.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationDTO implements Serializable {
    private Long id;
    private String message;
    private Long eventId;
    private LocalDateTime sentAt;
}
