package com.modzo.ors.domain.events;

class EventSerializationFailedException extends RuntimeException {
    EventSerializationFailedException(Class failedClass) {
        super(String.format("Class `%s` serialization failed", failedClass));
    }
}
