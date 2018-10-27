package com.mozdzo.ors.search.reader.parser;

import com.mozdzo.ors.domain.events.DomainEvent;
import com.mozdzo.ors.domain.events.Event;
import com.mozdzo.ors.domain.events.RadioStationStreamCreated;
import com.mozdzo.ors.search.RadioStationDocument;
import com.mozdzo.ors.search.RadioStationStreamDocument;
import com.mozdzo.ors.search.RadioStationsRepository;
import com.mozdzo.ors.search.ReadModelException;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
class RadioStationStreamCreatedEventParser implements EventParser {

    private final RadioStationsRepository radioStationsRepository;

    public RadioStationStreamCreatedEventParser(RadioStationsRepository radioStationsRepository) {
        this.radioStationsRepository = radioStationsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_CREATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamCreated.Data data = RadioStationStreamCreated.
                Data.deserialize(event.getBody());

        RadioStationDocument radioStationDocument = radioStationsRepository
                .findByUniqueId(data.getRadioStationUniqueId())
                .orElseThrow(() -> new ReadModelException(
                        "RADIO_STATION_BY_UNIQUE_ID_NOT_FOUND",
                        format("Radio station by unique id `%s` was not found",
                                data.getUniqueId())
                ));


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
