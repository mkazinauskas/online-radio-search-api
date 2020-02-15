package com.modzo.ors.search.domain.reader.parser;

import com.modzo.ors.domain.events.DomainEvent;
import com.modzo.ors.domain.events.Event;
import com.modzo.ors.domain.events.RadioStationStreamDeleted;
import com.modzo.ors.search.domain.RadioStationsRepository;
import org.springframework.stereotype.Component;

@Component
class RadioStationStreamDeletedEventParser implements EventParser {

    private final RadioStationsRepository radioStationsRepository;

    public RadioStationStreamDeletedEventParser(RadioStationsRepository radioStationsRepository) {
        this.radioStationsRepository = radioStationsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_DELETED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamDeleted.Data data = RadioStationStreamDeleted.Data.deserialize(event.getBody());

        radioStationsRepository.findByUniqueId(data.getRadioStationUniqueId())
                .ifPresent(station -> station.removeStream(data.getUniqueId()));
    }
}
