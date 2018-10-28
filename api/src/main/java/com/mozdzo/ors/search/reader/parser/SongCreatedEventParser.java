package com.mozdzo.ors.search.reader.parser;

import com.mozdzo.ors.domain.events.DomainEvent;
import com.mozdzo.ors.domain.events.Event;
import com.mozdzo.ors.domain.events.SongCreated;
import com.mozdzo.ors.search.SongDocument;
import com.mozdzo.ors.search.SongsRepository;
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

        songsRepository.save(new SongDocument(data.getUniqueId(), data.getTitle()));
    }
}
