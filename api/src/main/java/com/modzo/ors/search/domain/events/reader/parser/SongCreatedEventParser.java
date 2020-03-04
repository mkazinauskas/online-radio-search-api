package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.SongCreated;
import com.modzo.ors.search.domain.SongDocument;
import com.modzo.ors.search.domain.SongsRepository;
import org.springframework.stereotype.Component;

@Component
class SongCreatedEventParser implements EventParser {

    private final SongsRepository songsRepository;

    public SongCreatedEventParser(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.SONG_CREATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        SongCreated.Data data = SongCreated.Data.deserialize(event.getBody());

        songsRepository.save(
                new SongDocument(
                        data.getId(),
                        data.getUniqueId(),
                        data.getTitle()
                )
        );
    }
}
