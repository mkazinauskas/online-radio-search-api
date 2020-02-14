package com.modzo.ors.search.domain.reader.parser;

import com.modzo.ors.domain.events.DomainEvent;
import com.modzo.ors.domain.events.Event;
import com.modzo.ors.domain.events.RadioStationDeleted;
import com.modzo.ors.domain.events.SongDeleted;
import com.modzo.ors.search.domain.RadioStationsRepository;
import com.modzo.ors.search.domain.SongsRepository;
import org.springframework.stereotype.Component;

@Component
class SongDeletedEventParser implements EventParser {

    private final SongsRepository songsRepository;

    public SongDeletedEventParser(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.SONG_DELETED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        SongDeleted.Data data = SongDeleted.Data.deserialize(event.getBody());
        songsRepository.deleteByUniqueId(data.getUniqueId());
    }
}
