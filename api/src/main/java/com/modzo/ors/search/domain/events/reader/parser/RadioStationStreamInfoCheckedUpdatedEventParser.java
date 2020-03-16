package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationStreamInfoCheckedUpdated;
import com.modzo.ors.search.domain.RadioStationStreamDocument;
import com.modzo.ors.search.domain.RadioStationStreamsRepository;
import com.modzo.ors.search.domain.ReadModelException;
import org.springframework.stereotype.Component;

@Component
class RadioStationStreamInfoCheckedUpdatedEventParser implements EventParser {

    private final RadioStationStreamsRepository radioStationStreamsRepository;

    RadioStationStreamInfoCheckedUpdatedEventParser(RadioStationStreamsRepository radioStationStreamsRepository) {
        this.radioStationStreamsRepository = radioStationStreamsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_STREAM_INFO_CHECKED_UPDATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationStreamInfoCheckedUpdated.Data data = RadioStationStreamInfoCheckedUpdated.Data
                .deserialize(event.getBody());

        RadioStationStreamDocument streamDocument = radioStationStreamsRepository.findByUniqueId(data.getUniqueId())
                .stream()
                .filter(stream -> stream.getUniqueId().equals(data.getUniqueId()))
                .findFirst()
                .orElseThrow(() -> new ReadModelException(
                        "RADIO_STATION_STREAM_NOT_FOUND",
                        String.format("Radio station stream by unique id `%s`", data.getUniqueId())
                ));

        streamDocument.setSongsChecked(streamDocument.getSongsChecked());

        radioStationStreamsRepository.save(streamDocument);
    }
}
