package com.modzo.ors.search.domain.reader.parser;

import com.modzo.ors.domain.events.DomainEvent;
import com.modzo.ors.domain.events.Event;
import com.modzo.ors.domain.events.RadioStationCreated;
import com.modzo.ors.search.domain.RadioStationDocument;
import com.modzo.ors.search.domain.RadioStationsRepository;
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
