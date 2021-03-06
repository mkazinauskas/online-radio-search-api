package com.modzo.ors.stations.domain.radio.station.song.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSongs;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

public class FindRadioStationSongByPlayingTime {
    private final long radioStationId;

    private final ZonedDateTime playedTime;

    public FindRadioStationSongByPlayingTime(long radioStationId, ZonedDateTime playedTime) {
        this.radioStationId = radioStationId;
        this.playedTime = playedTime;
    }

    @Component
    public static class Handler {
        private final RadioStationSongs radioStationSongs;

        private final Validator validator;

        public Handler(RadioStationSongs radioStationSongs, Validator validator) {
            this.radioStationSongs = radioStationSongs;
            this.validator = validator;
        }

        @Transactional(readOnly = true)
        public Optional<RadioStationSong> handle(FindRadioStationSongByPlayingTime command) {
            validator.validate(command);
            return radioStationSongs.findByRadioStationIdAndPlayedTime(command.radioStationId, command.playedTime);
        }
    }

    @Component
    private static class Validator {
        private final RadioStations radioStations;

        Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(FindRadioStationSongByPlayingTime command) {
            if (command.radioStationId <= 0) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "radioStationId",
                        "Radio station id cannot be less or equal to zero");
            }

            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "radioStationId",
                        "Radio station with id is not available"
                );
            }

            if (command.playedTime == null) {
                throw new DomainException(
                        "FIELD_PLAYED_TIME_CANNOT_BE_NULL",
                        "playedTime",
                        "Song played time cannot be null"
                );
            }
        }
    }
}
