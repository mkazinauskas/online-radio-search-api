package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationStreamDeleted;
import org.springframework.stereotype.Component;

@Component
class RadioStationStreamDeletedEventParser implements EventParser {

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_DELETED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamDeleted.Data.deserialize(event.getBody());
        //Not required information
    }
}
