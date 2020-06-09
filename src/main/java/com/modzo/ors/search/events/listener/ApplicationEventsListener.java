package com.modzo.ors.search.events.listener;

import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Component
class ApplicationEventsListener implements ApplicationListener<StationsDomainEvent> {

    private final List<EventParser> eventParsers;

    ApplicationEventsListener(List<EventParser> eventParsers) {
        this.eventParsers = eventParsers;
    }

    @Override
    @Transactional(propagation = MANDATORY)
    public void onApplicationEvent(StationsDomainEvent domainEvent) {
        List<EventParser> parsers = eventParsers.stream()
                .filter(eventParser -> eventParser.type().equals(domainEvent.getType()))
                .filter(eventParser -> eventParser.action().equals(domainEvent.getAction()))
                .collect(Collectors.toList());

        Assert.isTrue(parsers.size() == 1, format("Expected 1 parser, but there are %s", parsers.size()));

        parsers.get(0).process(domainEvent);
    }
}
