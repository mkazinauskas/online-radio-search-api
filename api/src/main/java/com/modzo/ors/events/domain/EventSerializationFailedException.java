package com.modzo.ors.events.domain;

class EventSerializationFailedException extends RuntimeException {
    EventSerializationFailedException(Class failedClass) {
        super(String.format("Class `%s` serialization failed", failedClass));
    }
}
