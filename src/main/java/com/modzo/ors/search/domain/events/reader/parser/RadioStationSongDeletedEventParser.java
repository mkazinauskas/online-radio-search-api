package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationSongDeleted;
import org.springframework.stereotype.Component;

@Component
class RadioStationSongDeletedEventParser implements EventParser {

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_SONG_DELETED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationSongDeleted.Data.deserialize(event.getBody());
    }
}
