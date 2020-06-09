package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationUpdated;
import com.modzo.ors.search.domain.RadioStationDocument;
import com.modzo.ors.search.domain.RadioStationsRepository;
import com.modzo.ors.search.domain.commands.FindRadioStationByUniqueId;
import org.springframework.stereotype.Component;

@Component
class RadioStationUpdatedEventParser implements EventParser {

    private final FindRadioStationByUniqueId.Handler findRadioStationByUniqueId;

    private final RadioStationsRepository radioStationsRepository;

    public RadioStationUpdatedEventParser(
            FindRadioStationByUniqueId.Handler findRadioStationByUniqueId,
            RadioStationsRepository radioStationsRepository) {
        this.findRadioStationByUniqueId = findRadioStationByUniqueId;
        this.radioStationsRepository = radioStationsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_UPDATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationUpdated.Data data = RadioStationUpdated.Data.deserialize(event.getBody());

        RadioStationDocument radioStationDocument = findRadioStationByUniqueId.handle(
                new FindRadioStationByUniqueId(data.getUniqueId())
        );

        radioStationDocument.setTitle(data.getTitle());
        radioStationDocument.setWebsite(data.getWebsite());
        radioStationDocument.setEnabled(data.isEnabled());
        radioStationsRepository.save(radioStationDocument);
    }

}
