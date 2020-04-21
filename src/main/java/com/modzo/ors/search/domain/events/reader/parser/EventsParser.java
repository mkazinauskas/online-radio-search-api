package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.search.domain.events.parsedevents.ParsedEvent;
import com.modzo.ors.search.domain.events.parsedevents.ParsedEvents;
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
