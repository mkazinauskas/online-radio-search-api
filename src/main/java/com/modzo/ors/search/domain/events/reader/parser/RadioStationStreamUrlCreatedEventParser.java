package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationStreamUrlCreated;
import org.springframework.stereotype.Component;

@Component
class RadioStationStreamUrlCreatedEventParser implements EventParser {

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_URL_CREATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamUrlCreated.Data.deserialize(event.getBody());
        // We don't need this event. Checking only if it can be parsed.
    }
}
