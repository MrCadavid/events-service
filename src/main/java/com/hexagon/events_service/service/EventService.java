package com.hexagon.events_service.service;

import com.hexagon.events_service.dto.EventDTO;
import com.hexagon.events_service.dto.NotificationDTO;
import com.hexagon.events_service.entity.Event;
import com.hexagon.events_service.entity.Notification;
import com.hexagon.events_service.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

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
        LocalDateTime now = LocalDateTime.now();
        Event event = new Event(
                eventDTO.getId(),
                eventDTO.getType(),
                eventDTO.getResponsible(),
                eventDTO.getDate(),
                eventDTO.getLocation(),
                now,
                now
        );
        Event savedEvent = eventRepository.save(event);
        NotificationDTO notification = new NotificationDTO(null, "Event created", savedEvent.getId(), now);
        this.notificationService.post(notification);
        this.notificationService.notifyAll(notification);

        return new EventDTO(
                savedEvent.getId(),
                savedEvent.getType(),
                savedEvent.getResponsible(),
                savedEvent.getDate(),
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
            event.setLocation(eventDTO.getLocation());
            Event updatedEvent = eventRepository.save(event);

            NotificationDTO notification = new NotificationDTO(null, "Event updated", updatedEvent.getId(), LocalDateTime.now());
            this.notificationService.post(notification);
            this.notificationService.notifyAll(notification);

            return new EventDTO(
                    updatedEvent.getId(),
                    updatedEvent.getType(),
                    updatedEvent.getResponsible(),
                    updatedEvent.getDate(),
                    updatedEvent.getLocation()
            );
        }
        return null;
    }

    public boolean deleteEvent(Long id) { 
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            NotificationDTO notification = new NotificationDTO(null, "Event deleted", event.getId(), LocalDateTime.now());
            this.notificationService.post(notification);
            this.notificationService.notifyAll(notification);
            eventRepository.delete(event);
            return true;
        }
        return false;
    }

    public NotificationDTO createNotificationForEvent(Long id, NotificationDTO notification) {
        notification.setEventId(id);
        this.notificationService.post(notification);
        this.notificationService.notifyAll(notification);
        return notification;
    }

}
