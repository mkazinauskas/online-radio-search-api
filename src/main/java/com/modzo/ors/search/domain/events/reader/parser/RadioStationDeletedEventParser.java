package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationDeleted;
import com.modzo.ors.search.domain.RadioStationsRepository;
import org.springframework.stereotype.Component;

@Component
class RadioStationDeletedEventParser implements EventParser {

    private final RadioStationsRepository radioStationsRepository;

    public RadioStationDeletedEventParser(RadioStationsRepository radioStationsRepository) {
        this.radioStationsRepository = radioStationsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_DELETED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationDeleted.Data data = RadioStationDeleted.Data.deserialize(event.getBody());
        radioStationsRepository.deleteByUniqueId(data.getUniqueId());
    }
}
