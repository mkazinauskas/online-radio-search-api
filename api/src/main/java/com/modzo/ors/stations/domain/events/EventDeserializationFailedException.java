package com.modzo.ors.stations.domain.events;

class EventDeserializationFailedException extends RuntimeException {
    EventDeserializationFailedException(String body, Class failedClass) {
        super(String.format("Class `%s` deserialization failed from value `%s`", failedClass, body));
    }
}
