package com.modzo.ors.stations.resources.admin.events;

import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.Events;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class EventsController {

    private final Events events;

    public EventsController(Events events) {
        this.events = events;
    }

    @GetMapping("/admin/events")
    ResponseEntity<EventsModel> getEvents(Pageable pageable) {
        Page<Event> foundEvents = events.findAll(pageable);
        return ok(EventsModel.create(foundEvents, pageable));
    }

    @GetMapping("/admin/events/{id}")
    ResponseEntity<EventModel> getEvent(@PathVariable("id") long id) {
        Event foundEvent = events.findById(id).get();
        return ok(EventModel.create(foundEvent));
    }
}