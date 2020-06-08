package com.modzo.ors.statistics.provider;

import com.modzo.ors.search.domain.events.parsedevents.ParsedEvent;
import com.modzo.ors.search.domain.events.parsedevents.ParsedEvents;
import com.modzo.ors.search.domain.events.parsedevents.commands.FindLastParsedEvent;
import com.modzo.ors.statistics.StatisticProvider;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@Component
class SearchStatsProvider implements StatisticProvider {

    private final ParsedEvents parsedEvents;

    private final FindLastParsedEvent.Handler latestParsedEventHandler;

    SearchStatsProvider(ParsedEvents parsedEvents,
                        FindLastParsedEvent.Handler latestParsedEventHandler) {
        this.parsedEvents = parsedEvents;
        this.latestParsedEventHandler = latestParsedEventHandler;
    }

    @Override
    public Type type() {
        return Type.PARSED_EVENTS;
    }

    @Override
    public Map<Subtype, String> values() {
        Optional<ParsedEvent> latestEvent = latestParsedEventHandler.handle();
        return Map.ofEntries(
                Map.entry(Subtype.COUNT, String.valueOf(parsedEvents.count())),
                Map.entry(Subtype.LATEST_ENTRY_DATE, date(latestEvent)),
                Map.entry(Subtype.LATEST_ENTRY_ID, id(latestEvent))
        );
    }

    private String id(Optional<ParsedEvent> latestEvent) {
        return latestEvent
                .map(ParsedEvent::getEventId)
                .map(String::valueOf)
                .orElse("Not available");
    }

    private String date(Optional<ParsedEvent> latestEvent) {
        return latestEvent
                .map(ParsedEvent::getDate)
                .map(ZonedDateTime::toString)
                .orElse("Not available");
    }
}
