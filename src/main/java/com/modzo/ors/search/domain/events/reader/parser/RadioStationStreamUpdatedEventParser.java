package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationStreamUpdated;
import org.springframework.stereotype.Component;

@Component
class RadioStationStreamUpdatedEventParser implements EventParser {

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_UPDATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamUpdated.Data.deserialize(event.getBody());
        //Not required information
    }
}
