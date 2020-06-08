package com.modzo.ors.stations.domain.radio.station.song.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSongs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

public class GetRadioStationSongs {

    private final long radioStationId;

    private final Pageable pageable;

    public GetRadioStationSongs(long radioStationId, Pageable pageable) {
        this.radioStationId = radioStationId;
        this.pageable = requireNonNull(pageable);
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
        public Page<RadioStationSong> handle(GetRadioStationSongs command) {
            validator.validate(command);
            return radioStationSongs.findAllByRadioStationId(command.radioStationId, command.pageable);
        }
    }

    @Component
    private static class Validator {

        private final RadioStations radioStations;

        public Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(GetRadioStationSongs command) {
            if (command.radioStationId <= 0) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "radioStationId",
                        "Radio station id cannot be less or equal to zero"
                );
            }

            if (radioStations.findById(command.radioStationId).isEmpty()) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "radioStationId",
                        "Radio station with id is not available"
                );
            }
        }
    }
}
