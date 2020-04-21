package com.modzo.ors.stations.domain.song.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.song.Song;
import com.modzo.ors.stations.domain.song.Songs;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

public class GetSong {
    private final long songId;

    public GetSong(long songId) {
        this.songId = songId;
    }

    @Component
    public static class Handler {
        private final Songs songs;

        private final Validator validator;

        public Handler(Songs songs, Validator validator) {
            this.songs = songs;
            this.validator = validator;
        }

        @Transactional(readOnly = true)
        public Song handle(GetSong command) {
            validator.validate(command);
            return songs.findById(command.songId)
                    .orElseThrow(() -> new DomainException(
                            "SONG_BY_ID_NOT_FOUND",
                            "Song by id was not found")
                    );
        }
    }

    @Component
    private static class Validator {
        private final Songs songs;

        public Validator(Songs songs) {
            this.songs = songs;
        }

        void validate(GetSong command) {
            songs.findById(command.songId)
                    .orElseThrow(() -> new DomainException("SONG_WITH_ID_DOES_NOT_EXIST",
                            String.format("Song with id = %s does not exist", command.songId)));


            if (command.songId <= 0) {
                throw new DomainException("FIELD_SONG_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Song id cannot be less or equal to zero");
            }
        }
    }
}
