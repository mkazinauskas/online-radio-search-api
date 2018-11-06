package com.mozdzo.ors.search.reader.parser;

import com.mozdzo.ors.domain.events.DomainEvent;
import com.mozdzo.ors.domain.events.Event;
import com.mozdzo.ors.domain.events.RadioStationStreamUpdated;
import com.mozdzo.ors.search.RadioStationDocument;
import com.mozdzo.ors.search.RadioStationStreamDocument;
import com.mozdzo.ors.search.RadioStationsRepository;
import com.mozdzo.ors.search.ReadModelException;
import com.mozdzo.ors.search.commands.FindRadioStationByUniqueId;
import org.springframework.stereotype.Component;

@Component
class RadioStationStreamUpdatedEventParser implements EventParser {

    private final FindRadioStationByUniqueId.Handler findRadioStationByUniqueId;

    private final RadioStationsRepository radioStationsRepository;

    RadioStationStreamUpdatedEventParser(
            FindRadioStationByUniqueId.Handler findRadioStationByUniqueId,
            RadioStationsRepository radioStationsRepository) {
        this.findRadioStationByUniqueId = findRadioStationByUniqueId;
        this.radioStationsRepository = radioStationsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_UPDATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamUpdated.Data data = RadioStationStreamUpdated.Data.deserialize(event.getBody());

        RadioStationDocument radioStationDocument = findRadioStationByUniqueId.handle(
                new FindRadioStationByUniqueId(data.getRadioStationUniqueId())
        );

        RadioStationStreamDocument streamDocument = radioStationDocument.getStreams()
                .stream()
                .filter(stream -> stream.getUniqueId().equals(data.getUniqueId()))
                .findFirst()
                .orElseThrow(() -> new ReadModelException(
                        "RADIO_STATION_STREAM_NOT_FOUND",
                        String.format("Radio station stream by unique id `%s`", data.getUniqueId())
                ));

        streamDocument.setUrl(data.getUrl());
        streamDocument.setBitRate(data.getBitRate());
        streamDocument.setType(data.getType());

        radioStationsRepository.save(radioStationDocument);
    }
}
