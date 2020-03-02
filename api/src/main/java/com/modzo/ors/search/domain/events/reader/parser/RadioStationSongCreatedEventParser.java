package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.RadioStationSongCreated;
import com.modzo.ors.search.domain.RadioStationDocument;
import com.modzo.ors.search.domain.RadioStationSongDocument;
import com.modzo.ors.search.domain.RadioStationsRepository;
import com.modzo.ors.search.domain.SongDocument;
import com.modzo.ors.search.domain.commands.FindRadioStationByUniqueId;
import com.modzo.ors.search.domain.commands.FindSongByUniqueId;
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
