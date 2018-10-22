package com.mozdzo.ors.search.reader.parser;

import com.mozdzo.ors.domain.events.DomainEvent;
import com.mozdzo.ors.domain.events.Event;
import com.mozdzo.ors.domain.events.RadioStationCreated;
import com.mozdzo.ors.search.RadioStationDocument;
import com.mozdzo.ors.search.RadioStationsRepository;
import org.springframework.stereotype.Component;

@Component
class RadioStationCreatedEventParser implements EventParser {

    private final RadioStationsRepository radioStationsRepository;

    public RadioStationCreatedEventParser(RadioStationsRepository radioStationsRepository) {
        this.radioStationsRepository = radioStationsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_CREATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationCreated.Data data = RadioStationCreated.Data.deserialize(event.getBody());
        radioStationsRepository.save(new RadioStationDocument(data.getUniqueId(), data.getTitle()));
    }
}
