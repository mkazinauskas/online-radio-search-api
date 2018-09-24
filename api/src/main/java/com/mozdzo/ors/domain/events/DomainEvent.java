package com.mozdzo.ors.domain.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEvent;

abstract class DomainEvent extends ApplicationEvent {

    public DomainEvent(Object source) {
        super(source);
    }

    abstract static class Data {
    }

    abstract Event.Type type();

    abstract Data getData();

    String serialize() {
        try {
            return new ObjectMapper().writeValueAsString(this.getData());
        } catch (JsonProcessingException e) {
            throw new EventSerializationFailedException(this.getClass());
        }
    }
}
