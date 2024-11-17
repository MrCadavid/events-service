package com.hexagon.events_service.dto;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String type;
    private String responsible;  
    private LocalDate date;      
    private LocalTime time;      
    private String location;
}
