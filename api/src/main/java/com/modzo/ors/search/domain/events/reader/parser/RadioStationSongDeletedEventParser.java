package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationSongDeleted;
import com.modzo.ors.search.domain.RadioStationsRepository;
import org.springframework.stereotype.Component;

@Component
class RadioStationSongDeletedEventParser implements EventParser {

    private final RadioStationsRepository radioStationsRepository;

    public RadioStationSongDeletedEventParser(RadioStationsRepository radioStationsRepository) {
        this.radioStationsRepository = radioStationsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_SONG_DELETED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationSongDeleted.Data data = RadioStationSongDeleted.Data.deserialize(event.getBody());

        radioStationsRepository.findByUniqueId(data.getRadioStationUniqueId())
                .ifPresent(station -> {
                    station.removeSong(data.getUniqueId());
                    radioStationsRepository.save(station);
                });
    }
}
