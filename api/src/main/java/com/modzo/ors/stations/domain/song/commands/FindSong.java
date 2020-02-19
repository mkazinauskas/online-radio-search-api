package com.modzo.ors.stations.domain.song.commands;

import com.modzo.ors.stations.domain.song.Song;
import com.modzo.ors.stations.domain.song.Songs;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class FindSong {
    private final String title;

    public FindSong(String title) {
        this.title = title;
    }

    @Component
    public static class Handler {
        private final Songs songs;

        public Handler(Songs songs) {
            this.songs = songs;
        }

        @Transactional(readOnly = true)
        public Optional<Song> handle(FindSong command) {
            return songs.findByTitle(command.title);
        }
    }
}
