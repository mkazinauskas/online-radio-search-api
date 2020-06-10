package com.modzo.ors.search.events.listener;

import com.modzo.ors.search.domain.SongDocument;
import com.modzo.ors.search.domain.SongsRepository;
import com.modzo.ors.stations.domain.song.Song;
import com.modzo.ors.stations.domain.song.Songs;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class SongCreatedParser implements EventParser {

    private final SongsRepository songsRepository;

    private final Songs songs;

    public SongCreatedParser(SongsRepository songsRepository, Songs songs) {
        this.songsRepository = songsRepository;
        this.songs = songs;
    }

    @Override
    public StationsDomainEvent.Type type() {
        return StationsDomainEvent.Type.SONG;
    }

    @Override
    public StationsDomainEvent.Action action() {
        return StationsDomainEvent.Action.CREATED;
    }

    @Override
    public void process(StationsDomainEvent domainEvent) {
        Song savedSong = songs.findById(domainEvent.getId()).get();

        SongDocument songDocument = new SongDocument(savedSong.getId(), savedSong.getUniqueId(), savedSong.getTitle());
        songsRepository.save(songDocument);
    }
}
