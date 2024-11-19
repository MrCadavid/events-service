package com.hexagon.events_service.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.hexagon.events_service.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String type;
    private User responsible;  
    private LocalDateTime date;      
    private String location;
}
