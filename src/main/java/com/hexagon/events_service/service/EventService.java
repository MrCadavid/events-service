package com.hexagon.events_service.service;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.AmqpException;

import com.hexagon.events_service.dto.EventDTO;
import com.hexagon.events_service.dto.NotificationDTO;
import com.hexagon.events_service.entity.Event;
import com.hexagon.events_service.repository.EventRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EventService(EventRepository eventRepository, RabbitTemplate rabbitTemplate) {
        this.eventRepository = eventRepository;
        this.rabbitTemplate = rabbitTemplate;
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

        sendNotification("CREATED", "Event created successfully: " + savedEvent.getType());

        return new EventDTO(
                savedEvent.getId(),
                savedEvent.getType(),
                savedEvent.getResponsible(),
                savedEvent.getDate(),
                savedEvent.getTime(),
                savedEvent.getLocation()
        );
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

            sendNotification("UPDATED", "Event updated successfully: " + updatedEvent.getType());

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
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);

            sendNotification("DELETED", "Event deleted successfully with ID: " + id);

            return true;
        }
        return false;
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

    public EventDTO getEventById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        return eventOptional.map(event -> new EventDTO(
                event.getId(),
                event.getType(),
                event.getResponsible(),
                event.getDate(),
                event.getTime(),
                event.getLocation()
        )).orElse(null);
    }

    private void sendNotification(String type, String message) {
    
    }


    
    
    


}
