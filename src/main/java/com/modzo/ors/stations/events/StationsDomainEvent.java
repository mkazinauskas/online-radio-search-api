package com.modzo.ors.stations.events;

import org.springframework.context.ApplicationEvent;

import java.util.Objects;

public class StationsDomainEvent extends ApplicationEvent {

    public enum Action {
        CREATED,
        UPDATED,
        DELETED
    }

    public enum Type {
        RADIO_STATION,
        GENRE,
        SONG
    }

    private final Action action;

    private final Type type;

    private final long id;

    public StationsDomainEvent(Object source,
                               Action action,
                               Type type,
                               Long id) {
        super(source);
        this.action = action;
        this.type = type;
        this.id = Objects.requireNonNull(id);
    }

    public Action getAction() {
        return action;
    }

    public Type getType() {
        return type;
    }

    public long getId() {
        return id;
    }
}
