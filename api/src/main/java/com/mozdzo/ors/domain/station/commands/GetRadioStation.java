package com.mozdzo.ors.domain.station.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.station.RadioStation;
import com.mozdzo.ors.domain.station.RadioStations;
import org.springframework.stereotype.Component;

public class GetRadioStation {
    private final long id;

    public GetRadioStation(long id) {
        this.id = id;
    }

    @Component
    public static class Handler {
        private final RadioStations radioStations;

        private final Validator validator;

        Handler(RadioStations radioStations, Validator validator) {
            this.radioStations = radioStations;
            this.validator = validator;
        }

        public RadioStation handle(GetRadioStation command) {
            validator.validate(command);
            return radioStations.findById(command.id)
                    .orElseThrow(() -> new DomainException(
                            "RADIO_STATION_BY_ID_NOT_FOUND",
                            "Radio station by id was not found")
                    );
        }
    }

    @Component
    private static class Validator {
        void validate(GetRadioStation command) {
            if (command.id <= 0) {
                throw new DomainException("FIELD_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station id cannot be less or equal to zero");
            }
        }
    }
}
