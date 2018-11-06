package com.mozdzo.ors.search.reader.parser;

import com.mozdzo.ors.domain.events.DomainEvent;
import com.mozdzo.ors.domain.events.Event;
import com.mozdzo.ors.domain.events.RadioStationStreamCreated;
import com.mozdzo.ors.search.RadioStationDocument;
import com.mozdzo.ors.search.RadioStationStreamDocument;
import com.mozdzo.ors.search.RadioStationsRepository;
import com.mozdzo.ors.search.commands.FindRadioStationByUniqueId;
import org.springframework.stereotype.Component;

@Component
class RadioStationStreamCreatedEventParser implements EventParser {

    private final FindRadioStationByUniqueId.Handler findRadioStationByUniqueId;

    private final RadioStationsRepository radioStationsRepository;

    public RadioStationStreamCreatedEventParser(
            FindRadioStationByUniqueId.Handler findRadioStationByUniqueId,
            RadioStationsRepository radioStationsRepository) {
        this.findRadioStationByUniqueId = findRadioStationByUniqueId;
        this.radioStationsRepository = radioStationsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_CREATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamCreated.Data data = RadioStationStreamCreated.Data.deserialize(event.getBody());

        RadioStationDocument radioStationDocument = findRadioStationByUniqueId.handle(
                new FindRadioStationByUniqueId(data.getRadioStationUniqueId())
        );

        radioStationDocument.getStreams().add(
                new RadioStationStreamDocument(
                        data.getUniqueId(),
                        data.getRadioStationUniqueId(),
                        data.getUrl()
                )
        );

        radioStationsRepository.save(radioStationDocument);
    }
}
