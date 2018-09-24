package com.mozdzo.ors.domain.radio.station.song.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.radio.station.RadioStations;
import com.mozdzo.ors.domain.radio.station.song.Song;
import com.mozdzo.ors.domain.radio.station.song.Songs;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

public class FindSongByPlayingTime {
    private final long radioStationId;

    private final ZonedDateTime playedTime;

    public FindSongByPlayingTime(long radioStationId, ZonedDateTime playedTime) {
        this.radioStationId = radioStationId;
        this.playedTime = playedTime;
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
        public Optional<Song> handle(FindSongByPlayingTime command) {
            validator.validate(command);
            return songs.findByRadioStationIdAndPlayingTime(command.radioStationId, command.playedTime);
        }
    }

    @Component
    private static class Validator {
        private final RadioStations radioStations;

        public Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(FindSongByPlayingTime command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station id cannot be less or equal to zero");
            }

            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }

            if (command.playedTime == null) {
                throw new DomainException("FIELD_PLAYED_TIME_CANNOT_BE_NULL",
                        "Song played time cannot be null");
            }
        }
    }
}
