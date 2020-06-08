package com.modzo.ors.statistics.provider;

import com.modzo.ors.events.domain.Events;
import com.modzo.ors.statistics.StatisticProvider;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class EventsStatsProvider implements StatisticProvider {

    private final Events events;

    EventsStatsProvider(Events events) {
        this.events = events;
    }

    @Override
    public Type type() {
        return Type.EVENTS;
    }

    @Override
    public Map<Subtype, String> values() {
        return Map.ofEntries(
                Map.entry(Subtype.COUNT, String.valueOf(events.count()))
        );
    }
}
