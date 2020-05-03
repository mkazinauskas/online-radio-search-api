package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationCreated;
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

        var document = new RadioStationDocument(
                data.getId(),
                data.getUniqueId(),
                data.getTitle(),
                data.isEnabled()
        );

        radioStationsRepository.save(document);
    }
}