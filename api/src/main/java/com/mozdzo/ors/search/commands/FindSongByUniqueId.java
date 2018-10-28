package com.mozdzo.ors.search.commands;

import com.mozdzo.ors.search.ReadModelException;
import com.mozdzo.ors.search.SongDocument;
import com.mozdzo.ors.search.SongsRepository;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

public class FindSongByUniqueId {
    private final String uniqueId;

    public FindSongByUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Component
    public static class Handler {
        private final SongsRepository songsRepository;

        public Handler(SongsRepository songsRepository) {
            this.songsRepository = songsRepository;
        }

        public SongDocument handle(FindSongByUniqueId command) {
            return songsRepository.findByUniqueId(command.uniqueId)
                    .orElseThrow(() -> new ReadModelException(
                            "SONG_BY_UNIQUE_ID_NOT_FOUND",
                            format("Song by unique id `%s` was not found",
                                    command.uniqueId)
                    ));
        }
    }
}

