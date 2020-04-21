package com.modzo.ors.stations.resources.admin.events;

import com.modzo.ors.events.domain.Event;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class EventModel extends EntityModel<EventResponse> {

    private EventModel() {
    }

    private EventModel(EventResponse content, Link... links) {
        super(content, links);
    }

    static EventModel create(Event event) {
        EventResponse response = EventResponse.create(event);
        Link link = linkTo(methodOn(EventsController.class)
                .getEvent(response.getId()))
                .withSelfRel();
        return new EventModel(response, link);
    }
}
