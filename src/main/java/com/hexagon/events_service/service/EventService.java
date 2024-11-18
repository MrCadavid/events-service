package com.hexagon.events_service.service;

import com.hexagon.events_service.dto.EventDTO;
import com.hexagon.events_service.dto.NotificationDTO;
import com.hexagon.events_service.entity.Event;
import com.hexagon.events_service.repository.EventRepository;
import com.hexagon.events_service.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final NotificationService notificationService;

    @Autowired
    public EventService(EventRepository eventRepository, NotificationService notificationService) {
        this.eventRepository = eventRepository;
        this.notificationService = notificationService;
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = new Event(
                eventDTO.getId(),
                eventDTO.getType(),
                eventDTO.getResponsible(),
                eventDTO.getDate(),
                eventDTO.getTime(),
                eventDTO.getLocation()
        );
        Event savedEvent = eventRepository.save(event);

        NotificationDTO notification = new NotificationDTO(
        "CREATED", 
        "Event created",
        LocalDateTime.now(),
        savedEvent.getId());
        notificationService.notify(notification);

     
        return new EventDTO(
                savedEvent.getId(),
                savedEvent.getType(),
                savedEvent.getResponsible(),
                savedEvent.getDate(),
                savedEvent.getTime(),
                savedEvent.getLocation()
        );
    }

    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> new EventDTO(
                event.getId(),
                event.getType(),
                event.getResponsible(),
                event.getDate(),
                event.getTime(),
                event.getLocation()
        )).toList();
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setType(eventDTO.getType());
            event.setResponsible(eventDTO.getResponsible());
            event.setDate(eventDTO.getDate());
            event.setTime(eventDTO.getTime());
            event.setLocation(eventDTO.getLocation());
            Event updatedEvent = eventRepository.save(event);

            NotificationDTO notification = new NotificationDTO(
            "UPDATED", 
            "Event updated",
            LocalDateTime.now(),
            updatedEvent.getId());

            notificationService.notify(notification);

            return new EventDTO(
                    updatedEvent.getId(),
                    updatedEvent.getType(),
                    updatedEvent.getResponsible(),
                    updatedEvent.getDate(),
                    updatedEvent.getTime(),
                    updatedEvent.getLocation()
            );
        }
        return null;
    }

    public boolean deleteEvent(Long id) {
    Optional<Event> eventOptional = eventRepository.findById(id);
    if (eventOptional.isPresent()) {
        Event event = eventOptional.get();

        NotificationDTO notification = new NotificationDTO(
        "DELETED", 
        "Event deleted",
        LocalDateTime.now(),
        event.getId());
        notificationService.notify(notification);

        eventRepository.deleteById(id);
        return true;
    }
    return false;
}

    
}
