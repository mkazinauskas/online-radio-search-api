package com.mozdzo.ors.domain.radio.station.stream.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.radio.station.RadioStations;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStreams;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CreateRadioStationStream {
    private final long radioStationId;

    private final String url;

    public CreateRadioStationStream(long radioStationId, String url) {
        this.radioStationId = radioStationId;
        this.url = url;
    }

    private RadioStationStream toRadioStationStream() {
        return new RadioStationStream(this.radioStationId, this.url);
    }

    @Component
    public static class Handler {
        private final RadioStationStreams radioStationStreams;

        private final Validator validator;

        Handler(RadioStationStreams radioStationStreams, Validator validator) {
            this.radioStationStreams = radioStationStreams;
            this.validator = validator;
        }

        public Result handle(CreateRadioStationStream command) {
            validator.validate(command);
            RadioStationStream savedRadioStationStream = radioStationStreams.save(command.toRadioStationStream());
            return new Result(savedRadioStationStream.getId());
        }
    }

    @Component
    private static class Validator {

        private final RadioStations radioStations;

        public Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(CreateRadioStationStream command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_NOT_POSITIVE",
                        "Field radio station id should be positive");
            }
            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }
            if (isBlank(command.url)) {
                throw new DomainException("FIELD_URL_NOT_BLANK", "Field url cannot be blank");
            }
        }
    }

    public static class Result {
        public final long id;

        Result(long id) {
            this.id = id;
        }
    }
}
