package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationStreamCreated;
import org.springframework.stereotype.Component;

@Component
class RadioStationStreamCreatedEventParser implements EventParser {

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_CREATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamCreated.Data.deserialize(event.getBody());
        //Not required information
    }
}
