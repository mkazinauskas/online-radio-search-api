package com.mozdzo.ors.domain.radio.station.song.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.radio.station.RadioStations;
import com.mozdzo.ors.domain.radio.station.song.Song;
import com.mozdzo.ors.domain.radio.station.song.Songs;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStreams;
import org.springframework.stereotype.Component;

public class GetSong {
    private final long radioStationId;

    private final long songId;

    public GetSong(long radioStationId, long songId) {
        this.radioStationId = radioStationId;
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

        public Song handle(GetSong command) {
            validator.validate(command);
            return songs.findByRadioStationIdAndId(command.radioStationId, command.songId)
                    .orElseThrow(() -> new DomainException(
                            "SONG_BY_ID_NOT_FOUND",
                            "Radio station song by id was not found")
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
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station id cannot be less or equal to zero");
            }

            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }

            if (command.songId <= 0) {
                throw new DomainException("FIELD_SONG_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station song id cannot be less or equal to zero");
            }
        }
    }
}
