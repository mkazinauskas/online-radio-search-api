package com.modzo.ors.search.events.listener;

import com.modzo.ors.search.domain.SongDocument;
import com.modzo.ors.search.domain.SongsRepository;
import com.modzo.ors.stations.domain.song.Song;
import com.modzo.ors.stations.domain.song.Songs;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SongRefreshedParser implements EventParser {

    private final SongsRepository songsRepository;

    private final Songs songs;

    public SongRefreshedParser(SongsRepository songsRepository, Songs songs) {
        this.songsRepository = songsRepository;
        this.songs = songs;
    }

    @Override
    public StationsDomainEvent.Type type() {
        return StationsDomainEvent.Type.SONG;
    }

    @Override
    public StationsDomainEvent.Action action() {
        return StationsDomainEvent.Action.REFRESHED;
    }

    @Override
    public void process(StationsDomainEvent domainEvent) {
        Song savedSong = songs.findById(domainEvent.getId()).get();


        Optional<SongDocument> document = songsRepository.findById(domainEvent.getId());
        if (document.isPresent()) {
            SongDocument existingDocument = document.get();
            existingDocument.setTitle(savedSong.getTitle());
            songsRepository.save(existingDocument);
        } else {
            SongDocument newDocument = new SongDocument(
                    savedSong.getId(),
                    savedSong.getUniqueId(),
                    savedSong.getTitle()
            );
            songsRepository.save(newDocument);
        }
    }
}
