package com.mozdzo.ors.search.reader.parser;

import com.mozdzo.ors.domain.events.DomainEvent;
import com.mozdzo.ors.domain.events.Event;
import com.mozdzo.ors.domain.events.RadioStationSongCreated;
import com.mozdzo.ors.search.RadioStationDocument;
import com.mozdzo.ors.search.RadioStationSongDocument;
import com.mozdzo.ors.search.RadioStationsRepository;
import com.mozdzo.ors.search.SongDocument;
import com.mozdzo.ors.search.commands.FindRadioStationByUniqueId;
import com.mozdzo.ors.search.commands.FindSongByUniqueId;
import org.springframework.stereotype.Component;

@Component
class RadioStationSongCreatedEventParser implements EventParser {

    private final FindRadioStationByUniqueId.Handler findRadioStationHandler;

    private final FindSongByUniqueId.Handler findSongByUniqueId;

    private final RadioStationsRepository radioStationsRepository;

    public RadioStationSongCreatedEventParser(
            FindRadioStationByUniqueId.Handler findRadioStationHandler,
            FindSongByUniqueId.Handler findSongByUniqueId,
            RadioStationsRepository radioStationsRepository) {
        this.findRadioStationHandler = findRadioStationHandler;
        this.findSongByUniqueId = findSongByUniqueId;
        this.radioStationsRepository = radioStationsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_SONG_CREATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationSongCreated.Data data = RadioStationSongCreated.Data.deserialize(event.getBody());

        RadioStationDocument radioStationDocument = findRadioStationHandler.handle(
                new FindRadioStationByUniqueId(data.getRadioStationUniqueId())
        );

        SongDocument songDocument = findSongByUniqueId.handle(
                new FindSongByUniqueId(data.getSongUniqueId())
        );

        radioStationDocument.getSongs().add(
                new RadioStationSongDocument(
                        data.getUniqueId(),
                        data.getRadioStationUniqueId(),
                        songDocument.getTitle(),
                        data.getPlayedTime()
                )
        );

        radioStationsRepository.save(radioStationDocument);
    }
}
