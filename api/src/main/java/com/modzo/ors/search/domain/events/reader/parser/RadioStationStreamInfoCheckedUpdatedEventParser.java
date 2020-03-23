package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationStreamInfoCheckedUpdated;
import com.modzo.ors.search.domain.RadioStationDocument;
import com.modzo.ors.search.domain.RadioStationStreamDocument;
import com.modzo.ors.search.domain.RadioStationsRepository;
import com.modzo.ors.search.domain.ReadModelException;
import org.springframework.stereotype.Component;

@Component
class RadioStationStreamInfoCheckedUpdatedEventParser implements EventParser {

    private final RadioStationsRepository radioStationsRepository;

    public RadioStationStreamInfoCheckedUpdatedEventParser(RadioStationsRepository radioStationsRepository) {
        this.radioStationsRepository = radioStationsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_INFO_CHECKED_UPDATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamInfoCheckedUpdated.Data data = RadioStationStreamInfoCheckedUpdated.Data
                .deserialize(event.getBody());

        RadioStationDocument station = radioStationsRepository.findByUniqueId(data.getRadioStationUniqueId())
                .orElseThrow(() -> new ReadModelException(
                        "RADIO_STATION_NOT_FOUND",
                        String.format("Radio station by unique id `%s`", data.getRadioStationUniqueId())
                ));
        RadioStationStreamDocument stream = station.findStream(data.getUniqueId())
                .orElseThrow(() -> new ReadModelException(
                        "RADIO_STATION_STREAM_NOT_FOUND",
                        String.format("Radio station stream by unique id `%s`", data.getUniqueId())
                ));

        stream.setSongsChecked(data.getInfoChecked());

        radioStationsRepository.save(station);
    }
}
