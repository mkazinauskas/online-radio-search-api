package com.modzo.ors.search.events.listener;

import com.modzo.ors.search.domain.SongsRepository;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class SongDeletedParser implements EventParser {

    private final SongsRepository songsRepository;

    public SongDeletedParser(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    @Override
    public StationsDomainEvent.Type type() {
        return StationsDomainEvent.Type.SONG;
    }

    @Override
    public StationsDomainEvent.Action action() {
        return StationsDomainEvent.Action.DELETED;
    }

    @Override
    public void process(StationsDomainEvent domainEvent) {
        songsRepository.deleteById(domainEvent.getId());
    }
}
