package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationStreamDeleted;
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
                .ifPresent(station -> {
                    station.removeStream(data.getUniqueId());
                    radioStationsRepository.save(station);
                });
    }
}
