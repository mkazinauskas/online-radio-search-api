package com.modzo.ors.search.domain.reader.parser;

import com.modzo.ors.stations.domain.events.DomainEvent;
import com.modzo.ors.stations.domain.events.Event;
import com.modzo.ors.stations.domain.events.RadioStationDeleted;
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
