package com.mozdzo.ors.domain.events;

public class EventSerializationFailedException extends RuntimeException {
    EventSerializationFailedException(Class failedClass) {
        super(String.format("Class `%s` serialization failed", failedClass));
    }
}
