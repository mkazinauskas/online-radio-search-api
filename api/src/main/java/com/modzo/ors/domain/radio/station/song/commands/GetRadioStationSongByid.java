package com.modzo.ors.domain.radio.station.song.commands;

import com.modzo.ors.domain.DomainException;
import com.modzo.ors.domain.radio.station.RadioStations;
import com.modzo.ors.domain.radio.station.song.RadioStationSong;
import com.modzo.ors.domain.radio.station.song.RadioStationSongs;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

        void validate(GetRadioStationSongByid command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station id cannot be less or equal to zero");
            }

            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }

            if (command.id <= 0) {
                throw new DomainException("FIELD_SONG_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station song id cannot be less or equal to zero");
            }
        }
    }
}
