package com.mozdzo.ors.domain.song.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.radio.station.RadioStations;
import com.mozdzo.ors.domain.song.Song;
import com.mozdzo.ors.domain.song.Songs;
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
        private final RadioStations radioStations;

        public Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(GetSong command) {
            if (command.songId <= 0) {
                throw new DomainException("FIELD_SONG_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Song id cannot be less or equal to zero");
            }
        }
    }
}
