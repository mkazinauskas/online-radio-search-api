package com.modzo.ors.stations.resources.admin.events;

import com.modzo.ors.events.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class EventsModel extends PagedModel<EventModel> {

    private EventsModel() {
    }

    private EventsModel(Collection<EventModel> content, PageMetadata metadata, Link... links) {
        super(content, metadata, links);
    }

    static EventsModel create(Page<Event> events, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                events.getSize(),
                events.getNumber(),
                events.getTotalElements(),
                events.getTotalPages()
        );
        Collection<EventModel> resources = events.getContent()
                .stream()
                .map(EventModel::create)
                .collect(toList());

        Link link = linkTo(methodOn(EventsController.class)
                .getEvents(pageable)).withSelfRel();

        return new EventsModel(
                resources,
                pageMetadata,
                link
        );
    }
}
