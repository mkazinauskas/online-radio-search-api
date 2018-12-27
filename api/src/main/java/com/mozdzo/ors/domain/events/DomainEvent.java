package com.mozdzo.ors.domain.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationEvent;

import java.io.IOException;

public abstract class DomainEvent extends ApplicationEvent {

    DomainEvent(Object source) {
        super(source);
    }

    public abstract static class Data {
        public static <T extends Data> T deserialize(String body, Class<T> eventDataClass) {
            try {
                return EventObjectMapper.MAPPER.readValue(body, eventDataClass);
            } catch (IOException e) {
                throw new EventDeserializationFailedException(body, eventDataClass);
            }
        }
    }

    abstract Event.Type type();

    abstract Data getData();

    abstract String uniqueId();

    String serialize() {
        try {
            return EventObjectMapper.MAPPER.writeValueAsString(this.getData());
        } catch (JsonProcessingException e) {
            throw new EventSerializationFailedException(this.getClass());
        }
    }
}
