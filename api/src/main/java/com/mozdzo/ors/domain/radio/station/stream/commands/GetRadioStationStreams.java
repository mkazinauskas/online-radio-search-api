package com.mozdzo.ors.domain.radio.station.stream.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.radio.station.RadioStations;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStreams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

public class GetRadioStationStreams {
    private final long radioStationId;

    private final Pageable pageable;

    public GetRadioStationStreams(long radioStationId, Pageable pageable) {
        this.radioStationId = radioStationId;
        this.pageable = requireNonNull(pageable);
    }

    @Component
    public static class Handler {
        private final RadioStationStreams radioStationStreams;

        private final Validator validator;

        public Handler(RadioStationStreams radioStationStreams, Validator validator) {
            this.radioStationStreams = radioStationStreams;
            this.validator = validator;
        }

        @Transactional(readOnly = true)
        public Page<RadioStationStream> handle(GetRadioStationStreams command) {
            validator.validate(command);
            return radioStationStreams.findAllByRadioStationId(command.radioStationId, command.pageable);
        }
    }

    @Component
    private static class Validator {
        private final RadioStations radioStations;

        public Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(GetRadioStationStreams command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station id cannot be less or equal to zero");
            }

            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }
        }
    }
}
