package com.modzo.ors.domain.song.commands;

import com.modzo.ors.domain.song.Song;
import com.modzo.ors.domain.song.Songs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

public class GetSongs {
    private final Pageable pageable;

    public GetSongs(Pageable pageable) {
        this.pageable = requireNonNull(pageable);
    }

    @Component
    public static class Handler {
        private final Songs songs;

        public Handler(Songs songs) {
            this.songs = songs;
        }

        @Transactional(readOnly = true)
        public Page<Song> handle(GetSongs command) {
            return songs.findAll(command.pageable);
        }
    }
}
