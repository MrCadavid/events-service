package com.hexagon.events_service.entity;

import com.hexagon.events_service.entity.User;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type", nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private User responsible;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime date; 

    @Column(nullable = false)
    private String location;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
