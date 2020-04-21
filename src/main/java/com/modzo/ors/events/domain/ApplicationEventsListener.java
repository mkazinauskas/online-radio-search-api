package com.modzo.ors.events.domain;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Component
class ApplicationEventsListener implements ApplicationListener<DomainEvent> {

    private final Events events;

    ApplicationEventsListener(Events events) {
        this.events = events;
    }

    @Override
    @Transactional(propagation = MANDATORY)
    public void onApplicationEvent(DomainEvent domainEvent) {
        events.save(new Event(domainEvent.type(), domainEvent.uniqueId(), domainEvent.serialize()));
    }
}
