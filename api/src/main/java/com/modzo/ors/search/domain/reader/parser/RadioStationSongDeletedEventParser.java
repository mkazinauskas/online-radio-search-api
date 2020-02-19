package com.modzo.ors.search.domain.reader.parser;

import com.modzo.ors.stations.domain.events.DomainEvent;
import com.modzo.ors.stations.domain.events.Event;
import com.modzo.ors.stations.domain.events.RadioStationSongDeleted;
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
                .ifPresent(station -> station.removeSong(data.getUniqueId()));
    }
}
