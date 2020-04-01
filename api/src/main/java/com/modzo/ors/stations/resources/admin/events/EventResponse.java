package com.modzo.ors.stations.resources.admin.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.events.domain.Event;

import java.time.ZonedDateTime;

class EventResponse {

    private final long id;

    private final String entityUniqueId;

    private final ZonedDateTime created;

    private final Event.Type type;

    private final String body;

    @JsonCreator
    private EventResponse(@JsonProperty("id") long id,
                          @JsonProperty("entityUniqueId") String entityUniqueId,
                          @JsonProperty("created") ZonedDateTime created,
                          @JsonProperty("type") Event.Type type,
                          @JsonProperty("body") String body) {
        this.id = id;
        this.entityUniqueId = entityUniqueId;
        this.created = created;
        this.type = type;
        this.body = body;
    }

    static EventResponse create(Event event) {
        return new EventResponse(
                event.getId(),
                event.getEntityUniqueId(),
                event.getCreated(),
                event.getType(),
                event.getBody()
        );
    }

    public long getId() {
        return id;
    }

    public String getEntityUniqueId() {
        return entityUniqueId;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Event.Type getType() {
        return type;
    }

    public String getBody() {
        return body;
    }
}
