package com.hexagon.events_service.service;

import com.hexagon.events_service.dto.EventDTO;
import com.hexagon.events_service.entity.Event;
import com.hexagon.events_service.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
}
