package com.mozdzo.ors.search.reader.parser;

import com.mozdzo.ors.domain.events.Event;
import com.mozdzo.ors.domain.events.Events;
import com.mozdzo.ors.search.parsedevents.ParsedEvent;
import com.mozdzo.ors.search.parsedevents.ParsedEvents;
import com.mozdzo.ors.search.parsedevents.commands.FindLastParsedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventsProcessor {

    private final FindLastParsedEvent.Handler findLatestParsedEvent;
    private final Events events;
    private final ParsedEvents parsedEvents;
    private final EventsParser eventsParser;

    public EventsProcessor(FindLastParsedEvent.Handler findLatestParsedEvent,
                           Events events,
                           ParsedEvents parsedEvents,
                           EventsParser eventsParser) {
        this.findLatestParsedEvent = findLatestParsedEvent;
        this.events = events;
        this.parsedEvents = parsedEvents;
        this.eventsParser = eventsParser;
    }

    public void process() {
        if (parsedEvents.count() == 0) {
            firstEvent().ifPresent(eventsParser::parseEvent);
        }
        while (parsedEvents.count() < events.count()) {
            findLatestParsedEvent.handle()
                    .flatMap(this::nextEvent)
                    .ifPresent(eventsParser::parseEvent);
        }
    }

    private Optional<Event> firstEvent() {
        return events.findTop1ByIdGreaterThanOrderByIdAsc(0L);
    }

    private Optional<Event> nextEvent(ParsedEvent parsedEvent) {
        return events.findTop1ByIdGreaterThanOrderByIdAsc(parsedEvent.getId());
    }
}
