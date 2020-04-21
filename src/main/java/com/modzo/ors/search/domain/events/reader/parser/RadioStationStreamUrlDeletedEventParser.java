package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationStreamUrlDeleted;
import org.springframework.stereotype.Component;

@Component
class RadioStationStreamUrlDeletedEventParser implements EventParser {

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_URL_DELETED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamUrlDeleted.Data.deserialize(event.getBody());
        // We don't need this event. Checking only if it can be parsed.
    }
}
