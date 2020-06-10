package com.modzo.ors.stations.domain.radio.station.song.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSongs;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public class GetRadioStationSongByid {
    private final long radioStationId;

    private final long id;

    public GetRadioStationSongByid(long radioStationId, long id) {
        this.radioStationId = radioStationId;
        this.id = id;
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
        public RadioStationSong handle(GetRadioStationSongByid command) {
            validator.validate(command);
            return radioStationSongs.findByRadioStationIdAndId(command.radioStationId, command.id)
                    .orElseThrow(() -> new DomainException(
                            "RADIO_STATION_SONG_BY_ID_NOT_FOUND",
                            Set.of("radioStationId", "id"),
                            "Radio station song by id was not found"
                            )
                    );
        }
    }

    @Component
    private static class Validator {
        private final RadioStations radioStations;

        Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(GetRadioStationSongByid command) {
            if (command.radioStationId <= 0) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "radioStationId",
                        "Radio station id cannot be less or equal to zero");
            }

            if (radioStations.findById(command.radioStationId).isEmpty()) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "radioStationId",
                        "Radio station with id is not available"
                );
            }

            if (command.id <= 0) {
                throw new DomainException(
                        "FIELD_SONG_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "id",
                        "Radio station song id cannot be less or equal to zero"
                );
            }
        }
    }
}
