package com.modzo.ors.search.domain.reader.parser;

import com.modzo.ors.stations.domain.events.DomainEvent;
import com.modzo.ors.stations.domain.events.Event;
import com.modzo.ors.search.domain.parsedevents.ParsedEvent;
import com.modzo.ors.search.domain.parsedevents.ParsedEvents;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Component
class EventsParser {
    private final ParsedEvents parsedEvents;

    private final Map<Class, EventParser> eventsParsers;

    EventsParser(ParsedEvents parsedEvents, List<EventParser> eventsParsers) {
        this.parsedEvents = parsedEvents;
        this.eventsParsers = eventsParsers
                .stream()
                .collect(Collectors.toMap(EventParser::eventClass, item -> item));
    }

    public void parseEvent(Event event) {
        Class<? extends DomainEvent.Data> eventClass = event.getType().getEventClass();
        EventParser eventParser = eventsParsers.get(eventClass);
        if (eventParser == null) {
            throw new IllegalArgumentException(
                    format(
                            "Cannot find event parser for ",
                            eventClass.getName()
                    )
            );
        }
        eventParser.parse(event);
        parsedEvents.save(new ParsedEvent(event.getId()));
    }
}
