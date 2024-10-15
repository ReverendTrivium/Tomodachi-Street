package org.tomodachi.api;

import org.tomodachi.model.Event;
import org.tomodachi.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable UUID id) {
        return eventService.findById(id);
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        eventService.save(event);
        return event;
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable UUID id, @RequestBody Event event) {
        event.setId(id);
        eventService.save(event);
        return event;
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable UUID id) {
        eventService.deleteById(id);
    }
}
