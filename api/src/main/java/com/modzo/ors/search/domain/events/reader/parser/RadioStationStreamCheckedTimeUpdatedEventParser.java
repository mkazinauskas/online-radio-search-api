package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationStreamCheckedTimeUpdated;
import org.springframework.stereotype.Component;

@Component
class RadioStationStreamCheckedTimeUpdatedEventParser implements EventParser {

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_CHECKED_TIME_UPDATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamCheckedTimeUpdated.Data.deserialize(event.getBody());
        // We don't need this event. Checking only if it can be parsed.
    }
}
