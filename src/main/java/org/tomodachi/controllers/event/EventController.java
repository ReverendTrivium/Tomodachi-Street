package org.tomodachi.controllers.event;

import org.tomodachi.model.Event;
import org.tomodachi.service.EventService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // View all Events
    @GetMapping
    public String viewEvents(Model model) {
        model.addAttribute("events", eventService.findAll());
        return "events";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showCreateEventForm(Model model, Authentication authentication) {
        model.addAttribute("event", new Event());
        return "create_event"; // Render the create_event.html page
    }


    @PostMapping("/create")
    public String createEvent(@ModelAttribute Event event) {
        // No need to handle date parsing manually, Spring will automatically convert the dates.
        eventService.save(event);
        return "redirect:/events";
    }

    // Get a single event by ID
    @GetMapping("/view/{id}")
    public String viewEvent(@PathVariable UUID id, Model model) {
        Event event = eventService.findById(id);
        model.addAttribute("event", event);
        return "view_event";
    }

    // Update an existing event by ID
    @GetMapping("/edit/{id}")
    public String showEditEventForm(@PathVariable UUID id, Model model, Authentication authentication) {
        if (authentication == null || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/access-denied"; // Redirect to an access denied page or handle the error
        }

        Event event = eventService.findById(id);
        if (event == null) {
            return "redirect:/events";
        }
        model.addAttribute("event", event);
        return "edit_event";
    }

    @PostMapping("/edit/{id}")
    public String updateEvent(@PathVariable UUID id, @ModelAttribute Event event) {
        event.setId(id);
        eventService.save(event);
        return "redirect:/events";
    }

    // Delete an event by ID
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable UUID id, Authentication authentication) {
        // Check if the user is authenticated and has the admin role
        if (authentication == null || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/access-denied"; // Redirect to an access denied page or handle the error
        }

        // Perform the delete operation if the user has the admin role
        eventService.deleteById(id);
        return "redirect:/events";
    }
}

